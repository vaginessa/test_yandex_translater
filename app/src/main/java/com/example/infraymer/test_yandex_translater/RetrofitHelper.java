package com.example.infraymer.test_yandex_translater;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by infraymer on 17.03.17.
 */

public class RetrofitHelper {

    public static final String BASE_URL = "https://translate.yandex.net/";

    private static RetrofitHelper instance;

    private YandexAPI yandexAPI;

    private RetrofitHelper() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.yandexAPI = retrofit.create(YandexAPI.class);
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }

        return instance;
    }

    public YandexAPI getYandexAPI() {
        return yandexAPI;
    }
}
