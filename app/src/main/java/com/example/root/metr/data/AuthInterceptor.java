package com.example.root.metr.data;

import com.example.root.metr.root.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("authorization", App.getToken())
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
