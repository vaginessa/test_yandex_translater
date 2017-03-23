package com.example.infraymer.test_yandex_translater;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.infraymer.test_yandex_translater.JSON.LangsRespons;
import com.example.infraymer.test_yandex_translater.JSON.TranslateResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.language_in)
    Button mBtnLanguageIn;
    @BindView(R.id.swap_language)
    ImageButton mBtnSwapLanguage;
    @BindView(R.id.language_out)
    Button mBtnLanguageOut;
    @BindView(R.id.text_in)
    EditText mEtTextIn;
    @BindView(R.id.btn_copy)
    ImageButton mIbCopy;
    @BindView(R.id.btn_paste)
    ImageButton mIbPaste;
    @BindView(R.id.btn_clear)
    ImageButton mIbClear;
    @BindView(R.id.text_out)
    TextView mTvTextOut;

    public static final int LANG_IN = 0;
    public static final int LANG_OUT = 1;

    // Коллекции для массивов с языками
    public ArrayMap<String, String> langKeyArrayMap;
    public ArrayMap<String, String> keyLangArrayMap;
    public ArrayList<String> langsArrayList;

    // Список языков предиктора
    public ArrayList<String> listLangsPredictor;

    // Текущие коды языков
    public int currentItemLangIn;
    public int currentItemLangOut;

    // Текущие наименования языков
    public String currentNameLangIn;
    public String currentNameLangOut;

    // Текущий код направления перевода
    public String currentLangs;

    // Переменная активности предиктора
    public boolean activatePredictor;

    // Поля для работы с буферо обмена
    ClipboardManager mClipboard;
    ClipData mClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mClipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);

        initArrays();

        getListLanguagesTranslate();

        initEtTextIn();
    }

    // Инициализируем текстовое поле
    public void initEtTextIn() {
        mEtTextIn.setHorizontallyScrolling(false);
        mEtTextIn.setLines(5);
        mEtTextIn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Переводим текст
                    translate(mEtTextIn.getText().toString(), currentLangs);
                }
                return false;
            }
        });
    }

    // Инициализируем массивы
    public void initArrays() {
        langKeyArrayMap = new ArrayMap<>();
        langsArrayList = new ArrayList<>();
        listLangsPredictor = new ArrayList<>();
    }

    // Получаем массивы языков
    public void getListLanguagesTranslate() {
        RetrofitHelper.getInstance()
                .getYandexAPI()
                .getLangs()
                .enqueue(new Callback<LangsRespons>() {
                    @Override
                    public void onResponse(Call<LangsRespons> call, Response<LangsRespons> response) {

                        LangsRespons langsRespons = response.body();
                        keyLangArrayMap = langsRespons.getLangs();

                        for (int i = 0; i < keyLangArrayMap.size(); i++) {
                            langKeyArrayMap.put(keyLangArrayMap.valueAt(i), keyLangArrayMap.keyAt(i));
                            langsArrayList.add(keyLangArrayMap.valueAt(i));
                        }

                        currentItemLangIn = 15;
                        currentItemLangOut = 62;
                        currentNameLangIn = langsArrayList.get(currentItemLangIn);
                        currentNameLangOut = langsArrayList.get(currentItemLangOut);

                        setKeysLangs();

                        mBtnLanguageIn.setText(currentNameLangIn);
                        mBtnLanguageOut.setText(currentNameLangOut);
                    }

                    @Override
                    public void onFailure(Call<LangsRespons> call, Throwable t) {
                        Snackbar.make(
                            findViewById(R.id.main_content),
                            "Отсутствует интернет соединение",
                            Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    public void getListLanguagesPredictor() {
    }

    // Переводим текст
    public void translate(String text, String lang) {
        RetrofitHelper.getInstance()
                .getYandexAPI()
                .translate(text, lang, "plain")
                .enqueue(new Callback<TranslateResponse>() {
                    @Override
                    public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                        TranslateResponse translateResponse = response.body();
                        mTvTextOut.setText(translateResponse.getText()[0]);
                    }

                    @Override
                    public void onFailure(Call<TranslateResponse> call, Throwable t) {
                        Snackbar.make(
                            findViewById(R.id.main_content),
                            "Отсутствует интернет соединение",
                            Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    // Смена направления перевода
    public void swapLangs() {
        int a = currentItemLangIn;
        currentItemLangIn = currentItemLangOut;
        currentItemLangOut = a;

        String b = currentNameLangIn;
        currentNameLangIn = currentNameLangOut;
        currentNameLangOut = b;

        mBtnLanguageIn.setText(currentNameLangIn);
        mBtnLanguageOut.setText(currentNameLangOut);

        setKeysLangs();
    }

    // Заменяем данные текста переводом
    public void copyPasteTextOutToTextIn() {
        copyText(mTvTextOut.getText().toString());
        mEtTextIn.setText(pasteText());
        mEtTextIn.setSelection(mEtTextIn.getText().length());
    }

    // Диалог выбора языка
    public void choiceLang(final int type_lang) {
        String title = "";

        switch (type_lang) {
            case LANG_IN:
                title = "Язык текста";
                break;
            case LANG_OUT:
                title = "Язык перевода";
                break;
        }
        // Создаем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, langsArrayList);
        // Создаем новый диалог
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setSingleChoiceItems(adapter, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                // Тут надо описать реализацию выбора языка
                                switch (type_lang) {
                                    case LANG_IN:
                                        currentItemLangIn = item;
                                        currentNameLangIn = langsArrayList.get(item);
                                        mBtnLanguageIn.setText(String.valueOf(currentNameLangIn));
                                        activatePredictor();
                                        break;
                                    case LANG_OUT:
                                        currentItemLangOut = item;
                                        currentNameLangOut = langsArrayList.get(item);
                                        mBtnLanguageOut.setText(String.valueOf(currentNameLangOut));
                                        break;
                                }
                                setKeysLangs();
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();
    }

    // Установка кода направления перевода
    public void setKeysLangs(){
        String in = langKeyArrayMap.get(currentNameLangIn);
        String out = langKeyArrayMap.get(currentNameLangOut);

        currentLangs = in + "-" + out;
    }

    // Копировать в буфер
    public void copyText(String text) {
        mClip = ClipData.newPlainText("text", text);
        mClipboard.setPrimaryClip(mClip);
    }

    // Вставить из буфера
    public String pasteText() {
        return mClipboard.getPrimaryClip().getItemAt(0).getText().toString();
    }

    // Показать сообщение в снэкбаре
    public void showMessageSnackbar(String message) {
        Snackbar.make(findViewById(R.id.main_content), message, Snackbar.LENGTH_SHORT).show();
    }

    // Активация предиктора
    public void activatePredictor() {
        if (!listLangsPredictor.isEmpty()) {
            for (String lang : listLangsPredictor) {
                if (langKeyArrayMap.get(currentNameLangIn).equals(lang))
                    activatePredictor = true;
            }
        }
    }

    /***********************************************************************************************
     * Обработчики событий
     **********************************************************************************************/
    @OnClick(R.id.language_in)
    public void clickLanguageIn() {
        choiceLang(LANG_IN);
    }

    @OnClick(R.id.language_out)
    public void clickLanguageOut() {
        choiceLang(LANG_OUT);
    }

    @OnClick(R.id.swap_language)
    public void clickSwap() {
        swapLangs();
        copyPasteTextOutToTextIn();
    }

    @OnClick(R.id.btn_copy)
    public void clickCopy() {
        copyText(mEtTextIn.getText().toString());
        showMessageSnackbar("Текст скопирован");
    }

    @OnClick(R.id.btn_paste)
    public void clickPaste() {
        mEtTextIn.setText(mEtTextIn.getText().toString() + pasteText());
        mEtTextIn.setSelection(mEtTextIn.getText().length());
    }

    @OnClick(R.id.btn_clear)
    public void clickClear() {
        mEtTextIn.setText("");
    }

    @OnClick(R.id.text_out)
    public void clickTvTextOut() {
        copyText(mTvTextOut.getText().toString());
        showMessageSnackbar("Текст скопирован");
    }
}
