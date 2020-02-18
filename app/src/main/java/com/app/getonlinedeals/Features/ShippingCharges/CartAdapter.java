package com.app.getonlinedeals.Features.ShippingCharges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ItemCartsBinding;

import java.util.ArrayList;

import static androidx.databinding.DataBindingUtil.inflate;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private ArrayList<CartModel> postList;
    private LayoutInflater layoutInflater;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemCartsBinding binding;

        MyViewHolder(final ItemCartsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public CartAdapter(ArrayList<CartModel> postList) {
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            Context context = parent.getContext();
            layoutInflater = LayoutInflater.from(context);
        }
        ItemCartsBinding binding =
                inflate(layoutInflater, R.layout.item_carts, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.setImage((postList.get(position).getImage()));
        holder.binding.setName((postList.get(position).getTitle()));
        holder.binding.setQuantity("Quantity: " + (postList.get(position).getQuantity()));
        holder.binding.setColor("Color: " + postList.get(position).getColor());
        holder.binding.setPrice("$" + (postList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}