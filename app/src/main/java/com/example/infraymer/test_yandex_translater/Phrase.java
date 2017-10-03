package com.example.infraymer.test_yandex_translater;

/**
 * Created by infraymer on 17.04.17.
 */

public class Phrase {

    private String textIn;
    private String textOut;
    private Language langIn;
    private Language langOut;
    private boolean favourite;

    /***********************************************************************************************
     * Геттеры и сеттеры
     **********************************************************************************************/
    public String getTextIn() {
        return textIn;
    }

    public void setTextIn(String textIn) {
        this.textIn = textIn;
    }

    public String getTextOut() {
        return textOut;
    }

    public void setTextOut(String textOut) {
        this.textOut = textOut;
    }

    public Language getLangIn() {
        return langIn;
    }

    public void setLangIn(Language langIn) {
        this.langIn = langIn;
    }

    public Language getLangOut() {
        return langOut;
    }

    public void setLangOut(Language langOut) {
        this.langOut = langOut;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
