package com.wafer.wafertest;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wafer.wafertest.model.Country;
import java.util.HashMap;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Country> items;
    int lastInsertedIndex; // so we can add some more items for testing purposes
    int snapPosition;

    public void snapPosition(int position) {
        snapPosition = position;
        notifyDataSetChanged();
    }

    /** References to the views for each data item **/
    public class CountryViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView nameView;
        public TextView languageView;
        public TextView currencyView;
        public ImageView deleteView;

        public CountryViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout)v.findViewById(R.id.layout);
            nameView = (TextView) v.findViewById(R.id.nameView);
            languageView = (TextView) v.findViewById(R.id.languageView);
            currencyView = (TextView) v.findViewById(R.id.currencyView);
            deleteView = (ImageView) v.findViewById(R.id.deleteView);
        }
    }

    /** Constructor **/
    public CountryAdapter(List<Country> items) {
        this.items = items;
        lastInsertedIndex = items.size()-1;
        snapPosition = -1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Country item = items.get(position);
        CountryViewHolder vh = (CountryViewHolder) holder;
        vh.nameView.setText(item.getName());
        vh.languageView.setText(item.getLanguage());
        vh.currencyView.setText(item.getCurrency());
        if(snapPosition == position) {
            vh.deleteView.setVisibility(View.VISIBLE);
        } else {
            vh.deleteView.setVisibility(View.GONE);
        }
        //vh.data = item;
    }

    public void remove(int position) {
        Country item = items.get(position);
        if (items.contains(item)) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }
}
