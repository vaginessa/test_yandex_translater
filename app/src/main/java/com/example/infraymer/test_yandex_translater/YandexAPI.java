package com.example.infraymer.test_yandex_translater;

import com.example.infraymer.test_yandex_translater.JSON.LangsRespons;
import com.example.infraymer.test_yandex_translater.JSON.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by G_Dee on 20.02.2017.
 */

public interface YandexAPI {
    //@Headers({"content-type: application/json", "accept: application/json", "accept-language: ru_RU"})
    //@Headers("content-type: application/json")

    String API_KEY_TRANSLATE = "trnsl.1.1.20170321T114903Z.6cd423dc73bfc3ba.713804e81c39c1343ca0d0e3da0c602f962d6eee";
    String API_KEY_PREDICTOR = "pdct.1.1.20170323T071659Z.689167d7703d9001.cef0a38ca649e602036ff72c9e7d519ef6c9ce2a";
    String API_KEY_DICTIONARY = "dict.1.1.20170409T135647Z.5e9c4ca726199eaf.8d4d46bad5e6e65a27bfc51c8700d23a8baebdae";

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/v1.5/tr.json/getLangs?key=" + API_KEY_TRANSLATE + "&ui=ru")
    Call<LangsRespons> getLangs();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/v1.5/tr.json/translate?key=" + API_KEY_TRANSLATE)
    Call<TranslateResponse> translate(@Query("text") String text, @Query("lang") String lang, @Query("format") String format);
}
