package com.example.infraymer.test_yandex_translater.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.infraymer.test_yandex_translater.Adapters.LangsAdapter;
import com.example.infraymer.test_yandex_translater.JSON.LangsRespons;
import com.example.infraymer.test_yandex_translater.JSON.TranslateResponse;
import com.example.infraymer.test_yandex_translater.Language;
import com.example.infraymer.test_yandex_translater.R;
import com.example.infraymer.test_yandex_translater.RetrofitHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslatorFragment extends Fragment {

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

    // Поля для работы с языками
    private ArrayList<Language> mLanguages;
    private Language mLangIn;
    private Language mLangOut;

    // Поля для работы с буферо обмена
    ClipboardManager mClipboard;
    ClipData mClip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translator, container, false);
        ButterKnife.bind(this, view);

        mClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        initArrays();
        initEtTextIn();
        getListLanguagesTranslateFromServer();

        return view;
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
                    translate(mEtTextIn.getText().toString());
                }
                return false;
            }
        });
        mEtTextIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                translate(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // Инициализируем массивы
    public void initArrays() {
        mLanguages = new ArrayList<>();
    }

    // Сортировка языков

    // Заполняем массив
    private void fillListLangs(ArrayMap<String, String> map) {
        for (int i = 0; i < map.size(); i++) {
            mLanguages.add(new Language(map.valueAt(i), map.keyAt(i)));
        }

        setLangIn(15);
        setLangOut(1);
    }

    // Получить список языков с сервера
    public void getListLanguagesTranslateFromServer() {
        RetrofitHelper.getInstance(RetrofitHelper.TRANSLATE_URL)
                .getYandexAPI()
                .getLangs()
                .enqueue(new Callback<LangsRespons>() {
                    @Override
                    public void onResponse(Call<LangsRespons> call, Response<LangsRespons> response) {
                        fillListLangs(response.body().getLangs());
                    }

                    @Override
                    public void onFailure(Call<LangsRespons> call, Throwable t) {
                        Snackbar.make(
                                getActivity().findViewById(R.id.main_content),
                                R.string.no_internet,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    // Установить язык ввода
    private void setLangIn(Language lang) {
        mLangIn = lang;
        mBtnLanguageIn.setText(mLangIn.getName());
    }

    // Установить язык ввода по индексу в массиве
    private void setLangIn(int index) {
        setLangIn(mLanguages.get(index));
    }

    // Установить язык вывода
    private void setLangOut(Language lang) {
        mLangOut = lang;
        mBtnLanguageOut.setText(mLangOut.getName());
    }

    // Установить язык вывода по индексу в массиве
    private void setLangOut(int index) {
        setLangOut(mLanguages.get(index));
    }

    // Переводим текст
    public void translate(String text) {
        RetrofitHelper.getInstance(RetrofitHelper.TRANSLATE_URL)
                .getYandexAPI()
                .translate(text, getLang(), "plain")
                .enqueue(new Callback<TranslateResponse>() {
                    @Override
                    public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                        try {
                            TranslateResponse translateResponse = response.body();
                            mTvTextOut.setText(translateResponse.getText()[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mTvTextOut.setText("");
                        }

                    }

                    @Override
                    public void onFailure(Call<TranslateResponse> call, Throwable t) {
                        Snackbar.make(
                                getActivity().findViewById(R.id.main_content),
                                R.string.no_internet,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    // Получить направление перевода
    private String getLang() {
        return mLangIn.getKey() + "-" + mLangOut.getKey();
    }

    // Смена направления перевода
    public void swapLang() {
        Language langIn = mLangIn;
        setLangIn(mLangOut);
        setLangOut(langIn);
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
                title = getString(R.string.lang_text);
                break;
            case LANG_OUT:
                title = getString(R.string.lang_translate);
                break;
        }
        // Создаем адаптер данных
        LangsAdapter adapter =
                new LangsAdapter(getActivity(), android.R.layout.select_dialog_item, mLanguages);
        // Создаем новый диалог
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setSingleChoiceItems(adapter, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                // Тут надо описать реализацию выбора языка
                                switch (type_lang) {
                                    case LANG_IN:
                                        setLangIn(mLanguages.get(item));
                                        break;
                                    case LANG_OUT:
                                        setLangOut(mLanguages.get(item));
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();
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
    private void showMessageSnackbar(String message) {
        Snackbar.make(getActivity().findViewById(R.id.main_content), message, Snackbar.LENGTH_SHORT).show();
    }

    /***********************************************************************************************
     * Обработчики событий
     **********************************************************************************************/
    // Нажатие на кнопку языка ввода
    @OnClick(R.id.language_in)
    public void clickLanguageIn() {
        choiceLang(LANG_IN);
    }

    // нажатие на кнопку языка вывода
    @OnClick(R.id.language_out)
    public void clickLanguageOut() {
        choiceLang(LANG_OUT);
    }

    // Нажатие на кнопку смены перевода
    @OnClick(R.id.swap_language)
    public void clickSwap() {
        swapLang();
        copyPasteTextOutToTextIn();
    }

    // Нажатие на кнопку КОПИРОВАТЬ
    @OnClick(R.id.btn_copy)
    public void clickCopy() {
        copyText(mEtTextIn.getText().toString());
        showMessageSnackbar("Текст скопирован");
    }

    // Нажатие на кнопку ВСТАВИТЬ
    @OnClick(R.id.btn_paste)
    public void clickPaste() {
        mEtTextIn.setText(mEtTextIn.getText().toString() + pasteText());
        mEtTextIn.setSelection(mEtTextIn.getText().length());
    }

    // Нажатие на кнопку ОЧИСТИТЬ
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
