package com.example.root.metr.data;

import com.example.root.metr.root.App;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetrRetrofit {

    private static volatile IMetrApi mAPIServiceInstance;
    private static volatile IMetrApi mAPIServiceInstanceIntereapte;
    private static final String BASE_URL="";

    public static IMetrApi getApiService() {
        IMetrApi localInstance = mAPIServiceInstance;
        if (localInstance == null) {
            synchronized (IMetrApi.class) {
                localInstance = mAPIServiceInstance;
                if (localInstance == null) {
                    Retrofit retrofit = getRetrofit(false);
                    mAPIServiceInstance = localInstance = retrofit.create(IMetrApi.class);
                }
            }
        }
        return localInstance;
    }

    public static IMetrApi getApiServiceIntercept() {
        IMetrApi localInstance = mAPIServiceInstanceIntereapte;
        if (localInstance == null) {
            synchronized (IMetrApi.class) {
                localInstance = mAPIServiceInstanceIntereapte;
                if (localInstance == null) {
                    Retrofit retrofit = getRetrofit(true);
                    mAPIServiceInstanceIntereapte = localInstance = retrofit.create(IMetrApi.class);
                }
            }
        }
        return localInstance;
    }
    public static IMetrApi getApiServiceFile() {
        IMetrApi localInstance = mAPIServiceInstanceIntereapte;
        if (localInstance == null) {
            synchronized (IMetrApi.class) {
                localInstance = mAPIServiceInstanceIntereapte;
                if (localInstance == null) {
                    Retrofit retrofit = getRetrofitFile();
                    mAPIServiceInstanceIntereapte = localInstance = retrofit.create(IMetrApi.class);
                }
            }
        }
        return localInstance;
    }

    private static Retrofit getRetrofit(boolean intercept) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(intercept){
            builder.addInterceptor(new AuthInterceptor());
        }
        builder.addInterceptor(interceptor);
        builder.addInterceptor (new ChuckInterceptor(App.INSTANCE));
        builder.addNetworkInterceptor(new StethoInterceptor());

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
    }

    private static Retrofit getRetrofitFile() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new FileInterceptor());

        builder.addInterceptor(interceptor);
        builder.addInterceptor (new ChuckInterceptor(App.INSTANCE));
        builder.addNetworkInterceptor(new StethoInterceptor());

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
    }
}
