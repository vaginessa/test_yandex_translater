package com.example.infraymer.test_yandex_translater;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.infraymer.test_yandex_translater.JSON.LangsRespons;

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

    public ArrayMap<String, String> langKeyArrayMap;
    public ArrayMap<String, String> keyLangArrayMap;
    public ArrayList<String> langsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        langKeyArrayMap = new ArrayMap<>();

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
                        }
                    }

                    @Override
                    public void onFailure(Call<LangsRespons> call, Throwable t) {

                    }
                });
    }

    /***********************************************************************************************
     * Обработчики событий
     **********************************************************************************************/
    @OnClick(R.id.language_in)
    public void clickLanguageIn() {
        
    }
}
