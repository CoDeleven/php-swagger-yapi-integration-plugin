package com.codeleven.logic;

import com.codeleven.util.GzipRequestInterceptor;
import com.codeleven.util.JsonUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class YApiHttpClient {
    // method: post, data: 单个接口数据
    private static volatile YApiHttpClient client;
    private String yapiServerHost;
    private OkHttpClient okHttpClient;

    private YApiHttpClient() {
        okHttpClient = new OkHttpClient();
    }

    public static YApiHttpClient getInstance() {
        if (client == null) {
            synchronized (YApiHttpClient.class) {
                if (client == null) {
                    client = new YApiHttpClient();
                }
            }
        }
        return client;
    }

    /**
     * Good表示智能合并，使用智能合并模式上传swagger数据
     *
     * @param swaggerJson
     */
    public void updateSwaggerJsonInGood(String swaggerJson, String yapiServerHost, String projectToken) throws IOException {
        // TODO 压缩Json，压缩成一行
        String afterOneLine = JsonUtil.removeWhitespace(swaggerJson);
        // TODO GZIP压缩

        FormBody body = new FormBody
                .Builder()
                .add("type", "swagger")
                .add("merge", "good")
                // YAPI1.5.6 对于HTTP的报文大小有要求，笔者大概在500KB左右就会返回 code 413
                // 解决方案有两种，一种是调大 nodejs 服务的大小
                // 一种是对报文做压缩
                // 笔者打算采用第二种，第一种打算作为备选方案，不到万不得已不去动公司的服务器
                .add("json", swaggerJson)
                .add("token", projectToken)
                .build();

        Request request = new Request.Builder()
                .header("Accept-Encoding", "gzip")
                .url(yapiServerHost + "/api/open/import_data")
                .post(body)
                .build();
        Response response = okHttpClient.newBuilder()
                .addInterceptor(new GzipRequestInterceptor())
                .build()
                .newCall(request)
                .execute();
        if (response.code() != 200) {
            Notifications.Bus.notify(new Notification("codeleven-yapi", "YApi", "上传失败：\n HTTP CODE：" + response.code() + "\n " + response.message(), NotificationType.ERROR));
        }
        String responseBodyStr = response.body().string();
        if (JsonUtil.isJson(responseBodyStr)) {
            Map<String, Object> map = JsonUtil.transferJson2Map(responseBodyStr);
            int errorcode = (int) map.get("errcode");
            if (errorcode != 0) {
                Notifications.Bus.notify(new Notification("codeleven-yapi", "YApi", "上传失败：\n" + responseBodyStr, NotificationType.ERROR));
            } else {
                String errmsg = (String) map.get("errmsg");
                Notifications.Bus.notify(new Notification("codeleven-yapi", "YApi", "上传成功!" + errmsg, NotificationType.INFORMATION));
            }
        }
        response.close();
    }

}
