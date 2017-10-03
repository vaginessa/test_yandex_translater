package com.example.infraymer.test_yandex_translater.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infraymer.test_yandex_translater.R;

import java.util.List;
import java.util.Objects;

/**
 * Шаблон Recycler View Adapter
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.HistoryViewHolder> {

    /**
     * Класс ViewHolder
     */
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        /*
        Тут поля вьюшек

        TextView tv1;
        TextView tv2;
        Button btn1;
        ...

        Думаю что можно и через ButterKhife
        */

        public HistoryViewHolder(View itemView) {
            super(itemView);
            /*
            Тут привязка вьюшек к полям

            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2   = (TextView) itemView.findViewById(R.id.tv2);
            btn1   = (Button) itemView.findViewById(R.id.btn1);
            ...
            */
        }
    }

    /**
     * Тут пошел класс Adapter
     */

    // Объявляем список наших данных
    List<Objects> objects;

    FavouriteAdapter(List<Objects> objects) {
        this.objects = objects;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Здесь нужно указать наш list item и присвоить холдеру
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        /*
        А тут решаем что делать с вьюшками

        holder.tv1.setText(Format.getTime(objects.get(position).*));
        holder.tv2.setText(Format.getTime(objects.get(position).*));
        */
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
