package com.example.destiny_api_client.factory;

import android.text.TextUtils;

import com.example.destiny_api_client.auth.AuthenticationInterceptor;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://www.bungie.net/";

    private static OkHttpClient.Builder httpClient;

    private static Retrofit.Builder builder;

    private static Retrofit retrofit;

    static {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass
            , String clientId
            , String clientSecret
    ) {
        if (!TextUtils.isEmpty(clientId)
                && !TextUtils.isEmpty(clientSecret)) {
            String authToken = Credentials.basic(clientId, clientSecret);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
