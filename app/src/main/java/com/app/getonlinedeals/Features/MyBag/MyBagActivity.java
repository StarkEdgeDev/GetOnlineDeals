package com.app.getonlinedeals.Features.MyBag;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.DealsList.DealsListActivity;
import com.app.getonlinedeals.Features.ShippingCharges.Discount;
import com.app.getonlinedeals.Features.ShippingAddress.ShippingAddress;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityMyBagBinding;

import java.util.ArrayList;

public class MyBagActivity extends BaseActivity<ActivityMyBagBinding, MyBagPresenter> implements MyBagView {

    private ArrayList<CartModel> list;
    MyBagAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyBagActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_bag;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new MyBagPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(view -> finish());

        RecyclerView rvCarts = binding.rvCarts;
        rvCarts.setLayoutManager(new LinearLayoutManager(getActivityG()));
        list = getLocalData().getCartItems();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isFree()) {
                list.remove(i);
                getLocalData().setCartItems(list);
                break;
            }
        }
        list = getLocalData().getCartItems();
        getLocalData().saveDiscount(null);
        adapter = new MyBagAdapter(list, output -> {
            int position = Integer.parseInt(output.split(",")[0]);
            if (output.split(",")[1].equals("remove")) {
                list.remove(position);
            } else {
                list.get(position).setPrice(getPresenter().realPrice(list.get(position).getPrice(), list.get(position).getQuantity(), output.split(",")[1]));
                list.get(position).setQuantity(output.split(",")[1]);
            }
            getLocalData().setCartItems(list);
            binding.setTotalPrice("$" + getPresenter().totalPrice());
            adapter.notifyDataSetChanged();
        });
        rvCarts.setAdapter(adapter);
        binding.setTotalPrice("$" + getPresenter().totalPrice());

        binding.btnProceedToCheckout.setOnClickListener(view -> {
            if (getLocalData().getShippingAddress() == null) {
                ShippingAddress.start(getActivityG());
            } else {
                Discount.start(getActivityG(), getLocalData().getShippingAddress());
            }
        });
        binding.tvContinueShopping.setOnClickListener(view -> {
            Intent i = new Intent(getActivityG(), DealsListActivity.class);        // Specify any activity here e.g. home or splash or login etc
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    @Override
    public Context getActivityG() {
        return MyBagActivity.this;
    }
}
