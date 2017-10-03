package com.example.infraymer.test_yandex_translater.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.infraymer.test_yandex_translater.Data.TranslatorContract.HistoryEntry;

/**
 * Created by infraymer on 20.04.17.
 */

public class TranslatorDbHelper extends SQLiteOpenHelper{

    // Имя БД
    private static final String DATABASE_NAME = "translator.db";

    // Версия БД
    private static final int DATABASE_VERSION = 1;


    public TranslatorDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " ("
                + HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HistoryEntry.COLUMN_LANG_IN + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_LANG_OUT + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_TEXT_IN + " INTEGER NOT NULL DEFAULT 3, "
                + HistoryEntry.COLUMN_TEXT_OUT + " INTEGER NOT NULL, "
                + HistoryEntry.COLUMN_FAVOURITE + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
