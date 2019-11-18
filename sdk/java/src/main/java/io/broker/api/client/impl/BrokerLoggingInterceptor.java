package io.broker.api.client.impl;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BrokerLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = null;

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = getCharset(requestBody.contentType());
            body = buffer.readString(charset);
        }

        if (log.isDebugEnabled()) {
            log.debug("Request method：[{}] url：[{}] headers: [{}] body：[{}]",
                    request.method(), request.url(), request.headers(), body);
        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = getCharset(responseBody.contentType());
            rBody = buffer.clone().readString(charset);
        }

        if (log.isDebugEnabled()) {
            log.debug("Response code: [{}] message: [{}] body：[{}] reqTime: [{}]ms",
                    response.code(), response.message(), rBody, tookMs);
        }

        return response;
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = null;
        if (contentType != null) {
            charset = contentType.charset();
        }

        if (charset == null) {
            charset = UTF8;
        }

        return charset;
    }
}
