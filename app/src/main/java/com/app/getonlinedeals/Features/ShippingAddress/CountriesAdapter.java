package com.app.getonlinedeals.Features.ShippingAddress;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;

import java.util.ArrayList;

public class CountriesAdapter extends BaseAdapter implements SpinnerAdapter {

    ArrayList<CountriesListModel.Countries> countries;
    Context context;
    private BaseCallBack<Integer> callBack;
    private String country;

    public CountriesAdapter(Context context, ArrayList<CountriesListModel.Countries> countries, BaseCallBack<Integer> callBack) {
        this.countries = countries;
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_varients, null);
        TextView textView = view.findViewById(R.id.tvVariant);
        textView.setText(countries.get(position).getName());
        callBack.onCallBack(position);
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.item_varients, null);
        final TextView textView = view.findViewById(R.id.tvVariant);
        textView.setText(countries.get(position).getName());
        return view;
    }
}