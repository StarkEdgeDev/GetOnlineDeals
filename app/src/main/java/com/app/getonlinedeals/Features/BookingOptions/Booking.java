package com.app.getonlinedeals.Features.BookingOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.BillingAddress.AddressModel;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.Features.ShippingCharges.CartAdapter;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityBookingBinding;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Booking extends BaseActivity<ActivityBookingBinding, BookingPresenter> implements BookingView {
    private static final String CONFIG_CLIENT_ID = "AbsIVrhK9LE7TmwB_KjXsDrh9n_uh5er0OUinu74epqZISS60T1Nk3dnqkDy4i0rwoMH_XgcSf0DKpxh";
    private static final int REQUEST_CODE_PAYMENT = 1;
    final static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(CONFIG_CLIENT_ID);
    private CartAdapter cartAdapter;
    ArrayList<CartModel> cartList = new ArrayList<>();
    //https://github.com/paypal/PayPal-Android-SDK/issues/336   //4012001037167778
    private boolean isShowCart = false;
    private String effectivePrice = "";
    private AddressModel shippingAddress, billingAddress;

    public static void start(Context context, String discountCode, AddressModel billing, AddressModel shippingAddress) {
        Intent starter = new Intent(context, Booking.class);
        starter.putExtra("billingAddress", billing);
        starter.putExtra("shippingAddress", shippingAddress);
        starter.putExtra("discountCode", discountCode);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new BookingPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        binding.btnPayment.setOnClickListener(view -> {
            if (binding.getShippingType() == null) {
                displayError("Some error accrued. Please try later");
                return;
            }
            PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(effectivePrice), "USD",
                    "GetOnlineDeals", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent1 = new Intent(getActivityG(), PaymentActivity.class);
            intent1.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            startActivityForResult(intent1, REQUEST_CODE_PAYMENT);
        });

        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(view -> finish());

        billingAddress = (AddressModel) getIntent().getSerializableExtra("billingAddress");
        shippingAddress = (AddressModel) getIntent().getSerializableExtra("shippingAddress");
        String discountCode = getIntent().getStringExtra("discountCode");

        getPresenter().checkOut(discountCode, billingAddress, shippingAddress);

        binding.setBillingAddress(billingAddress.getAddress() + " , " + billingAddress.getCity() + " , " +
                billingAddress.getState() + " , " + billingAddress.getCountry() + " , " + billingAddress.getPin());
        binding.setShippingAddress(shippingAddress.getAddress() + " , " + shippingAddress.getCity() + " , " +
                shippingAddress.getState() + " , " + shippingAddress.getCountry() + " , " + shippingAddress.getPin());
        binding.setEmail(shippingAddress.getEmail());

        RecyclerView rvCarts = binding.rvCarts;
        rvCarts.setLayoutManager(new LinearLayoutManager(getActivityG()));
        cartAdapter = new CartAdapter(cartList);
        rvCarts.setAdapter(cartAdapter);

        binding.tvOrderSummary.setOnClickListener(view -> {
            isShowCart = !isShowCart;
            if (isShowCart) {
                cartList.addAll(getLocalData().getCartItems());
            } else {
                cartList.clear();
            }
            cartAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public Context getActivityG() {
        return Booking.this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    getPresenter().placeOrders(getLocalData().getDiscount(), shippingAddress, billingAddress);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                displayError("Payment cancelled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                displayError("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void shippingCharges(CheckoutResponse output) {
        String price;
        if (output.getResponse().get(0).getPrice().equals("0")) {
            price = "Free";
        } else {
            price = output.getResponse().get(0).getPrice();
        }
        binding.setShippingPrice(price);
        binding.setShippingType(output.getResponse().get(0).getTitle());
        binding.setTotalPrice("$" + output.getResponse().get(0).getCheckout().getSubtotal_price());
        effectivePrice = output.getResponse().get(0).getCheckout().getSubtotal_price();
    }

    @Override
    public void bookingResponse(String id) {
        if (id != null) {
            displayError("Booking successful");
            getLocalData().setCartItems(new ArrayList<>());
        }
    }
}
