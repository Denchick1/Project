package com.example.root.metr.data;

import com.example.root.metr.root.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class FileInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("authorization", App.getToken())
                .addHeader("content-type:","multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .build();
        Response response = chain.proceed(request);
        return response;
    }

}
