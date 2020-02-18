package com.app.getonlinedeals.Features.DealsList;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ItemDealListBinding;

import java.util.ArrayList;

import static androidx.databinding.DataBindingUtil.inflate;

public class DealsListAdapter extends RecyclerView.Adapter<DealsListAdapter.MyViewHolder> {

    private ArrayList<DealsResponse.Products> postList;
    private LayoutInflater layoutInflater;
    private BaseCallBack<String> baseCallBack;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemDealListBinding binding;

        MyViewHolder(final ItemDealListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    DealsListAdapter(ArrayList<DealsResponse.Products> postList, BaseCallBack<String> baseCallBack) {
        this.postList = postList;
        this.baseCallBack = baseCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemDealListBinding binding =
                inflate(layoutInflater, R.layout.item_deal_list, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.binding.setImage((postList.get(position).getImage().getSrc()));
        holder.binding.setName(postList.get(position).getTitle());
        holder.binding.setDiscountedPrice("$" + postList.get(position).getVariants().get(0).getPrice());
        holder.binding.setPrice("$" + postList.get(position).getVariants().get(0).getCompare_at_price());
        String percent = "0";
        if (postList.get(position).getVariants().get(0).getCompare_at_price() != null) {
            double discPrice = Double.parseDouble(postList.get(position).getVariants().get(0).getCompare_at_price()) -
                    Double.parseDouble(postList.get(position).getVariants().get(0).getPrice());

            double discPercent = discPrice
                    / Double.parseDouble(postList.get(position).getVariants().get(0).getCompare_at_price()) * 100;
            percent = String.valueOf(discPercent).substring(0,2);

        } else {
            holder.binding.setPrice("");
        }
        holder.binding.setPercentSaved("YOU SAVE: (" + percent + "%)");
        holder.binding.tvPrice.setPaintFlags(holder.binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseCallBack.onCallBack(postList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


}