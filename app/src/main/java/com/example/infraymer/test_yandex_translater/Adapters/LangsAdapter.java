package com.example.infraymer.test_yandex_translater.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.infraymer.test_yandex_translater.Language;

import java.util.ArrayList;

/**
 * Created by infraymer on 15.04.17.
 */

public class LangsAdapter extends ArrayAdapter<Language> {
    public LangsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Language> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Language language = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, null);
        }
        assert language != null;
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(language.getName());

        return convertView;
    }
}
