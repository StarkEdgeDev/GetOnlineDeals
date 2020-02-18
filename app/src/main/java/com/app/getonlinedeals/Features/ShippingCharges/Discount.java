package com.app.getonlinedeals.Features.ShippingCharges;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.BillingAddress.AddressModel;
import com.app.getonlinedeals.Features.BillingAddress.BillingAddress;
import com.app.getonlinedeals.Features.BookingOptions.Booking;
import com.app.getonlinedeals.Features.DealDetails.DealDetailsResponse;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.Features.ShippingAddress.ShippingAddress;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityDiscountBinding;

import java.util.ArrayList;

public class Discount extends BaseActivity<ActivityDiscountBinding, DiscountPresenter> implements DiscountView, View.OnClickListener {
    private CartAdapter cartAdapter;
    private ArrayList<CartModel> cartList = new ArrayList<>();
    ArrayList<CartModel> cartDiscountAdd;

    public static void start(Context context, AddressModel addressModel) {
        Intent starter = new Intent(context, Discount.class);
        starter.putExtra("addressModel", addressModel);
        context.startActivity(starter);
    }

    AddressModel shippingAddress;

    boolean isMatched = false;
    boolean isShowCart = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_discount;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new DiscountPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        ImageView ivApplyCode = binding.ivApplyCode;
        TextView tvChangeShipping = binding.tvChangeShipping;
        TextView tvChangeEmail = binding.tvChangeEmail;
        TextView tvOrderSummary = binding.tvOrderSummary;
        Button btnAddShipping = binding.btnAddShipping;
        Button btnWithoutShipping = binding.btnWithoutShipping;
        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < cartDiscountAdd.size(); i++) {
                    if (cartDiscountAdd.get(i).isFree()) {
                        cartDiscountAdd.remove(i);
                        getLocalData().setCartItems(cartDiscountAdd);
                        break;
                    }
                }
                finish();
            }
        });
        cartDiscountAdd = new ArrayList<>(getLocalData().getCartItems());

        binding.setIsApplied(false);
        shippingAddress = (AddressModel) getIntent().getSerializableExtra("addressModel");
        binding.setEmail(shippingAddress.getEmail());
        binding.setShippingAddress(shippingAddress.getAddress() + " , " + shippingAddress.getCity() + " , " +
                shippingAddress.getState() + " , " + shippingAddress.getCountry() + " , " + shippingAddress.getPin());
        binding.setTotalPrice("$" + getPresenter().totalPrice());

        RecyclerView rvCarts = binding.rvCarts;
        rvCarts.setLayoutManager(new LinearLayoutManager(getActivityG()));
        cartAdapter = new CartAdapter(cartList);
        rvCarts.setAdapter(cartAdapter);

        tvChangeEmail.setOnClickListener(this);
        ivApplyCode.setOnClickListener(this);
        tvChangeShipping.setOnClickListener(this);
        tvOrderSummary.setOnClickListener(this);
        btnWithoutShipping.setOnClickListener(this);
        btnAddShipping.setOnClickListener(this);
    }

    @Override
    public Context getActivityG() {
        return Discount.this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOrderSummary:
                isShowCart = !isShowCart;
                if (isShowCart) {
                    cartList.addAll(getLocalData().getCartItems());
                } else {
                    cartList.clear();
                }
                cartAdapter.notifyDataSetChanged();
                break;
            case R.id.btnAddShipping:
                BillingAddress.start(getActivityG(), binding.getDiscountCodePrint(), shippingAddress);
                break;
            case R.id.btnWithoutShipping:
                //getPresenter().checkOut(binding.getDiscountCodePrint(), shippingAddress, shippingAddress);
                Booking.start(getActivityG(), binding.getDiscountCodePrint(), shippingAddress, shippingAddress);
                break;
            case R.id.tvChangeShipping:
            case R.id.tvChangeEmail:
                ShippingAddress.start(getActivityG());
                finish();
                break;
            case R.id.ivApplyCode:
                if (isMatched) {
                    displayError("Al'ready applied discount code");
                    return;
                }
                if (binding.getDiscountCode() == null || binding.getDiscountCode().isEmpty()) {
                    displayError("Please enter discount code");
                    return;
                }
                getPresenter().getDiscounts();
                break;
        }
    }

    @Override
    public void onDiscountRes(DiscountResponse output) {
        for (int i = 0; i < output.getPriceRules().size(); i++) {
            if (binding.getDiscountCode().equals(output.getPriceRules().get(i).getTitle())) {
                if (output.getPriceRules().get(i).getPrerequisite_product_ids().isEmpty()) {
                    isMatched = true;
                    binding.setIsApplied(true);
                    binding.setTotalPrice("$" + getPresenter().getEffectivePrice(output.getPriceRules().get(i)));
                    binding.setDiscountCodePrint(binding.getDiscountCode());
                    getLocalData().saveDiscount(output.getPriceRules().get(i));
                } else {
                    int quantity = 0;
                    for (int j = 0; j < cartDiscountAdd.size(); j++) {
                        if (cartDiscountAdd.get(j).getProductId().equals(output.getPriceRules().get(i).getPrerequisite_product_ids().get(0))) {
                            quantity = quantity + Integer.parseInt(cartDiscountAdd.get(j).getQuantity());
                        }
                    }
                    int reQuantity = output.getPriceRules().get(i).getPrerequisite_to_entitlement_quantity_ratio()
                            .getPrerequisite_quantity();
                    if (reQuantity <= quantity) {
                        ArrayList<String> entitledProducts = output.getPriceRules().get(i).getEntitled_product_ids();
                        for (int j = 0; j < entitledProducts.size(); j++) {
                            getPresenter().implementDiscount(entitledProducts.get(j));
                        }
                        isMatched = true;
                        binding.setIsApplied(true);
                        binding.setDiscountCodePrint(binding.getDiscountCode());
                        getLocalData().saveDiscount(output.getPriceRules().get(i));
                    } else {
                        if (quantity == 0) {
                            displayError("Please add items to your cart first");
                            return;
                        }
                        displayError("Quantity is less to get benefit");
                    }
                }
            }
        }
        if (!isMatched) {
            displayError("Invalid discount code");
        }
    }

    @Override
    public void productDetails(DealDetailsResponse output) {
        CartModel cartModel = new CartModel(output.getProduct().getId(),
                output.getProduct().getVariants().get(0).getId(), "1",
                output.getProduct().getImage().getSrc(), output.getProduct().getTitle(),
                output.getProduct().getVariants().get(0).getPrice(),
                output.getProduct().getVariants().get(0).getTitle(), true);
        boolean isInCart = false;
        for (int i = 0; i < cartDiscountAdd.size(); i++) {
            if (cartModel.getVariantId().equals(output.getProduct().getVariants().get(0).getId()) && cartDiscountAdd.get(i).isFree()) {
                isInCart = true;
            }
        }
        if (!isInCart) {
            cartDiscountAdd.add(cartModel);
            getLocalData().setCartItems(cartDiscountAdd);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (int i = 0; i < cartDiscountAdd.size(); i++) {
            if (cartDiscountAdd.get(i).isFree()) {
                cartDiscountAdd.remove(i);
                getLocalData().setCartItems(cartDiscountAdd);
                break;
            }
        }
    }
}
