package com.example.infraymer.test_yandex_translater.Data;

import android.provider.BaseColumns;

/**
 * Created by infraymer on 20.04.17.
 */

public class TranslatorContract {

    private TranslatorContract() {

    }

    public static final class HistoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "guests";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_LANG_IN = "lang_in";
        public final static String COLUMN_LANG_OUT = "lang_out";
        public final static String COLUMN_TEXT_IN = "text_in";
        public final static String COLUMN_TEXT_OUT = "text_out";
        public final static String COLUMN_FAVOURITE = "text_out";

        public static final int FAVOURITE_FALSE = 0;
        public static final int FAVOURITE_TRUE = 1;
    }
}
