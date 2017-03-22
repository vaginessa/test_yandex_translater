package com.example.infraymer.test_yandex_translater;

import com.example.infraymer.test_yandex_translater.JSON.LangsRespons;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by G_Dee on 20.02.2017.
 */

public interface YandexAPI {
    //@Headers({"content-type: application/json", "accept: application/json", "accept-language: ru_RU"})
    //@Headers("content-type: application/json")

    String API_KEY = "trnsl.1.1.20170321T114903Z.6cd423dc73bfc3ba.713804e81c39c1343ca0d0e3da0c602f962d6eee";

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/v1.5/tr.json/getLangs?key=" + API_KEY + "&ui=ru")
    Call<LangsRespons> getLangs();
}
