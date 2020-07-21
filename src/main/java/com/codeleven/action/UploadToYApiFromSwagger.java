package com.codeleven.action;

import com.codeleven.config.ConfigInfo;
import com.codeleven.config.ConfigInfoManager;
import com.codeleven.logic.YApiHttpClient;
import com.codeleven.util.ErrorLogUtil;
import com.codeleven.util.JsonUtil;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.yourkit.util.FileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UploadToYApiFromSwagger extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ConfigInfo configInfo = ConfigInfoManager.getInstance(e.getProject()).getConfigInfo();
        boolean result = this.validateConfig(configInfo);
        if (!result) {
            return;
        }

        PhpInterpreter interpreter = PhpProjectConfigurationFacade.getInstance(e.getProject()).getInterpreter();
        if (interpreter == null) {
            Notifications.Bus.notify(new Notification("codeleven-yapi", "YApi", "请配置PHP解释器...\n可查阅文档：http://192.168.2.18:2017/ysphp/phpstorm-plugin/php-swagger-yapi-integration-plugin#%E9%85%8D%E7%BD%AEPHP%E8%A7%A3%E9%87%8A%E5%99%A8", NotificationType.ERROR));
            return;
        }
        if (configInfo.isEnableDebug()) {
            Notifications.Bus.notify(new Notification("codeleven-yapi", "YApi",
                    "当前使用的配置：" + JsonUtil.prettyPrint(configInfo), NotificationType.INFORMATION));
        }

        // php的可执行目录
        String phpExeHomePath = interpreter.getHomePath();
        String logFile = configInfo.getSwaggerResultSavePath() + "test.log";
        GeneralCommandLine generalCommandLine = new GeneralCommandLine(phpExeHomePath,
                "-d error_log=" + logFile,
                configInfo.getSwaggerExecutablePath(),
                "-d",
                "-o",
                configInfo.getSwaggerResultSavePath(),
                configInfo.getSwaggerScanPath());
        ApplicationManager.getApplication().saveAll();

        ProgressManager.getInstance().runProcessWithProgressSynchronously(() -> {
            try {
                ProgressIndicator indicatorBase = ProgressManager.getInstance().getProgressIndicator();
                indicatorBase.setFraction(0.3);
                indicatorBase.setText2("扫描中...");
                Process process = generalCommandLine.createProcess();
                process.waitFor(60, TimeUnit.SECONDS);
                indicatorBase.setFraction(0.6);

                indicatorBase.setText2("数据检查...");
                boolean hasError = ErrorLogUtil.hasError(logFile);
                if (hasError) {
                    Notifications.Bus.notify(new Notification("codeleven-yapi",
                            "YApi",
                            ErrorLogUtil.returnPrettyError(logFile),
                            NotificationType.ERROR));
                }
//                ErrorLogUtil.deleteFile(logFile);
                if (hasError) return;
                indicatorBase.setFraction(0.8);

                indicatorBase.setText2("扫描结束，正在上传YApi...");
                String swaggerJson = FileUtil.readFileContentAsUtf8(new File(configInfo.getSwaggerResultSavePath() + "swagger.json"));

                YApiHttpClient.getInstance().updateSwaggerJsonInGood(swaggerJson, configInfo.getYapiServerHost(), configInfo.getProjectToken());
                indicatorBase.setFraction(1);
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // 处理完毕后，执行上传
        }, "正在扫描PHP文件...", true, e.getProject());


    }

    protected boolean validateConfig(ConfigInfo configInfo) {
        String phpSwaggerExecutablePath = configInfo.getSwaggerExecutablePath();
        if (phpSwaggerExecutablePath == null || phpSwaggerExecutablePath.isEmpty()) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "swagger可执行文件路径为空...请配置", NotificationType.ERROR);
            return false;
        }
        String phpSwaggerScanPath = configInfo.getSwaggerScanPath();
        if (phpSwaggerScanPath == null || phpSwaggerScanPath.isEmpty()) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "swagger扫描路径为空...请配置", NotificationType.ERROR);
            return false;
        }
        if (!phpSwaggerScanPath.endsWith("/") && !phpSwaggerScanPath.endsWith("\\")) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "swagger扫描路径结尾不能有斜杠", NotificationType.ERROR);
            return false;
        }

        String phpSwaggerResultSavePath = configInfo.getSwaggerResultSavePath();
        if (phpSwaggerResultSavePath == null || phpSwaggerResultSavePath.isEmpty()) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "swagger.json保存路径为空...请配置", NotificationType.ERROR);
            return false;
        }
        if (!phpSwaggerResultSavePath.endsWith("/") && !phpSwaggerResultSavePath.endsWith("\\")) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "swagger.json保存路径结尾不能有斜杠", NotificationType.ERROR);
            return false;
        }

        String yapiServerHost = configInfo.getYapiServerHost();
        if (yapiServerHost == null || yapiServerHost.isEmpty()) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "YAPI Server主机地址为空...请配置", NotificationType.ERROR);
            return false;
        }
        if (yapiServerHost.endsWith("/") || yapiServerHost.endsWith("\\")) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "YAPI Server主机地址最后不能有斜杠", NotificationType.ERROR);
            return false;
        }

        String projectToken = configInfo.getProjectToken();
        if (projectToken == null || projectToken.isEmpty()) {
            ErrorLogUtil.reportError("codeleven-yapi", "YApi", "ProjectToken为空...请配置", NotificationType.ERROR);
            return false;
        }
        return true;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {

    }
}
