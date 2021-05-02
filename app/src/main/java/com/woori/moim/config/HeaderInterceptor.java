package com.woori.moim.config;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @Override
    @NonNull
    public Response intercept(@NonNull final Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();

        builder.addHeader("appKey", "l7xxy3A5e00R4e5Gges8SWbEgPZTL1Xyqfx1");
        builder.addHeader("Content-Type","application/json");

        return chain.proceed(builder.build());
    }
}
