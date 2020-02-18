package com.app.getonlinedeals.Features.MyBag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.OnClickEvent;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ItemMyBagBinding;

import java.util.ArrayList;

import static androidx.databinding.DataBindingUtil.inflate;

public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.MyViewHolder> {

    private ArrayList<CartModel> postList;
    private LayoutInflater layoutInflater;
    private BaseCallBack<String> baseCallBack;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemMyBagBinding binding;

        MyViewHolder(final ItemMyBagBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    MyBagAdapter(ArrayList<CartModel> postList, BaseCallBack<String> baseCallBack) {
        this.postList = postList;
        this.baseCallBack = baseCallBack;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(context);
        }
        ItemMyBagBinding binding =
                inflate(layoutInflater, R.layout.item_my_bag, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.setImage((postList.get(position).getImage()));
        holder.binding.setName((postList.get(position).getTitle()));
        holder.binding.setQuantity((postList.get(position).getQuantity()));
        holder.binding.setColor("Color: " + postList.get(position).getColor());
        holder.binding.setCancel("Edit");
        holder.binding.setIsEdit(false);
        holder.binding.setPrice((postList.get(position).getPrice()));
        holder.binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.setIsEdit(!holder.binding.getIsEdit());
                if (holder.binding.getIsEdit()) {
                    holder.binding.setCancel("Cancel");
                } else {
                    holder.binding.setCancel("Edit");
                }
            }
        });
        holder.binding.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseCallBack.onCallBack(position + ",remove");
            }
        });
        holder.binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.getQuantity().equals("0")) return;
                holder.binding.setIsEdit(false);
                holder.binding.setCancel("Edit");
                baseCallBack.onCallBack(position + "," + holder.binding.getQuantity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}