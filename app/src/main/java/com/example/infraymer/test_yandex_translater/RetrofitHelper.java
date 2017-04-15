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

    public static final String TRANSLATE_URL = "https://translate.yandex.net/";
    public static final String DICTIONARY_URL = "https://translate.yandex.net/";
    public static final String PREDICTOR_URL = "https://translate.yandex.net/";

    private static RetrofitHelper instance;

    private YandexAPI yandexAPI;

    private RetrofitHelper(String url) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.yandexAPI = retrofit.create(YandexAPI.class);
    }

    public static RetrofitHelper getInstance(String url) {
        if (instance == null) {
            instance = new RetrofitHelper(url);
        }

        return instance;
    }

    public YandexAPI getYandexAPI() {
        return yandexAPI;
    }
}
