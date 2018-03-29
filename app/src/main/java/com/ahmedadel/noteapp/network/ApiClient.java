package com.ahmedadel.noteapp.network;

import com.ahmedadel.noteapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * ApiClient that handles all the logic behind retrofit initializing.
 */

public class ApiClient {

    private static final int REQUEST_TIMEOUT = 60;
    private final static ApiClient apiClient = new ApiClient();

    private ApiClient() {
        getClient();
    }

    public static ApiClient getInstance() {
        return apiClient;
    }

    public Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(initOkHttp())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-apikey", BuildConfig.API_KEY);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return httpClient.build();
    }
}