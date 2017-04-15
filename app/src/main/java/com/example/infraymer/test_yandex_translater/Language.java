package com.example.infraymer.test_yandex_translater;

import android.support.annotation.NonNull;

/**
 * Created by infraymer on 09.04.17.
 */

public class Language {

    @NonNull
    private String name;

    @NonNull
    private String key;

    public Language(@NonNull String name, @NonNull String key) {
        this.name = name;
        this.key = key;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getKey() {
        return key;
    }
}
