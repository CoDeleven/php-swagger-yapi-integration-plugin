package com.codeleven.util;

import okhttp3.*;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class GzipRequestInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        if (original.body() == null || original.header("Content-Encoding") != null) {
            return chain.proceed(original);
        }
        Request compressedRequest = original.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(original.method(), gzip(original.body()))
                .build();
        return chain.proceed(compressedRequest);
    }

    private RequestBody gzip(final RequestBody requestBody) {
        return new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() throws IOException {
                return -1;
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(bufferedSink));
                requestBody.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}
