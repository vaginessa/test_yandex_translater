package com.example.infraymer.test_yandex_translater.JSON;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by infraymer on 22.03.17.
 */

public class LangsRespons {

    @SerializedName("dirs")
    @Expose
    private ArrayList<String> dirs;
    @SerializedName("langs")
    @Expose
    private ArrayMap<String, String> langs;

    public ArrayList<String> getDirs() {
        return dirs;
    }

    public ArrayMap<String, String> getLangs() {
        return langs;
    }
}
