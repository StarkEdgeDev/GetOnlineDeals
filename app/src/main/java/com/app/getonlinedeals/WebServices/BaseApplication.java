/*
 * Copyright (c) 2017. Code by mohit . Happy coding
 */

package com.app.getonlinedeals.WebServices;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApplication extends Application {

    private static Retrofit retrofit;
    private static Retrofit retrofit2;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            initRetrofitModule();
        }
        return retrofit;
    }

    public static Retrofit getRetrofit2() {
        if (retrofit2 == null) {
            initRetrofitModule2();
        }
        return retrofit2;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(getApplicationContext());
        //for camera
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initRetrofitModule();
        initRetrofitModule2();
    }

    private static void initRetrofitModule() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Basic Nzk0OWFjM2M3MjBlNmRhZmEzNzI1ZjE3YTBjYTExMDY6NDIzOWY3YzUwZDQ0NjdmYmYzMmU0NzM5NWE3ZWI0NWI=")
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Web.Path.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private static void initRetrofitModule2() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .build();
        retrofit2 = new Retrofit.Builder()
                .baseUrl(Web.Path.BASE_UR2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
