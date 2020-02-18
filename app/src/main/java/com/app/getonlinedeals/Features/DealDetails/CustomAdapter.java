package com.app.getonlinedeals.Features.DealDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.app.getonlinedeals.Features.DealsList.DealsResponse;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private ArrayList<DealsResponse.Variants> variants;
    private BaseCallBack<Integer> callBack;

    CustomAdapter(Context context, ArrayList<DealsResponse.Variants> variants, BaseCallBack<Integer> callBack) {
        this.context = context;
        this.variants = variants;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return variants.size();
    }

    @Override
    public Object getItem(int position) {
        return variants.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(context, R.layout.item_varients, null);
        TextView textView = view.findViewById(R.id.tvVariant);
        textView.setText(variants.get(position).getTitle());
        callBack.onCallBack(position);
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_varients, null);
        final TextView textView = view.findViewById(R.id.tvVariant);
        textView.setText(variants.get(position).getTitle());
        return view;
    }
}