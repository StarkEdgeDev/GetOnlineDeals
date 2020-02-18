package com.app.getonlinedeals.Features.DealDetails;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Features.DealsList.DealsResponse;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ItemDealListBinding;
import com.app.getonlinedeals.databinding.ItemImageBinding;

import java.util.ArrayList;

import static androidx.databinding.DataBindingUtil.inflate;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    private ArrayList<DealsResponse.Image> postList;
    private LayoutInflater layoutInflater;
    private BaseCallBack<String> baseCallBack;
    private int row_index = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemImageBinding binding;

        MyViewHolder(final ItemImageBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    ImagesAdapter(ArrayList<DealsResponse.Image> postList, BaseCallBack<String> baseCallBack) {
        this.postList = postList;
        this.baseCallBack = baseCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            Context context = parent.getContext();
            layoutInflater = LayoutInflater.from(context);
        }
        ItemImageBinding binding =
                inflate(layoutInflater, R.layout.item_image, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.binding.setImage(postList.get(position).getSrc());
        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseCallBack.onCallBack(postList.get(position).getSrc());
                row_index=position;
                notifyDataSetChanged();
            }
        });
        holder.binding.setIsSelected(row_index==position);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


}