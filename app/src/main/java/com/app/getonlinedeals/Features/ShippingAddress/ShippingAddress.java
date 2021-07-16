package com.app.getonlinedeals.Features.ShippingAddress;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.BillingAddress.AddressModel;
import com.app.getonlinedeals.Features.ShippingCharges.CartAdapter;
import com.app.getonlinedeals.Features.ShippingCharges.Discount;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityShippingAddressBinding;

import java.util.ArrayList;

public class ShippingAddress extends BaseActivity<ActivityShippingAddressBinding, ShippingAddressPresenter> implements
        ShippingAddressView {
    private ArrayList<CountriesListModel.Countries> countries = new ArrayList<>();
    private ArrayList<CountriesListModel.Provinces> states = new ArrayList<>();
    private Spinner spCountry, spState;
    private String countryName, stateName;
    private CountriesAdapter adapter;
    private StatesAdapter statesAdapter;
    private CartAdapter cartAdapter;
    private ArrayList<CartModel> cartList = new ArrayList<>();
    boolean isShowCart = false;

    public static void start(Context context) {
        Intent starter = new Intent(context, ShippingAddress.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_shipping_address;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new ShippingAddressPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(view -> finish());

        binding.tvOrderSummary.setOnClickListener(view -> {
            isShowCart = !isShowCart;
            if (isShowCart) {
                cartList.addAll(getLocalData().getCartItems());
            } else {
                cartList.clear();
            }
            cartAdapter.notifyDataSetChanged();
        });
        RecyclerView rvCarts = binding.rvCarts;
        rvCarts.setLayoutManager(new LinearLayoutManager(getActivityG()));
        cartAdapter = new CartAdapter(cartList);
        rvCarts.setAdapter(cartAdapter);
        binding.setTotalPrice("$" + getPresenter().totalPrice());

        getPresenter().getCountries();
        spCountry = binding.spCountry;
        spState = binding.spState;
        adapter = new CountriesAdapter(getActivityG(), countries, output -> {
            countryName = countries.get(output).getCode();
            states.clear();
            states.addAll(countries.get(output).getProvinces());
            statesAdapter.notifyDataSetChanged();
        });
        statesAdapter = new StatesAdapter(getActivityG(), states, output -> stateName = states.get(output).getCode());
        spCountry.setAdapter(adapter);
        spState.setAdapter(statesAdapter);

        binding.setIsSave(false);

        if (getLocalData().getShippingAddress() != null) {
            AddressModel data = getLocalData().getShippingAddress();
            binding.setAddress(data.getAddress());
            binding.setEmail(data.getEmail());
            binding.setIsSave(true);
            binding.setFirstName(data.getFirstName());
            binding.setLastName(data.getLastName());
            binding.setPhone(data.getPhone());
            binding.setPin(data.getPin());
            binding.setCity(data.getCity());
        }

        binding.btnAddShipping.setOnClickListener(view -> {
            if (getPresenter().isChecked()) {
                AddressModel addressModel = new AddressModel(city(), address(), lastName(), firstName(), email(),
                        state(), country(), pin(), phone());
                if (isSave()) {
                    getLocalData().saveShippingAddress(addressModel);
                }
                Discount.start(getActivityG(), addressModel);
            }
        });
    }

    @Override
    public Context getActivityG() {
        return ShippingAddress.this;
    }

    @Override
    public String email() {
        return binding.getEmail();
    }

    @Override
    public String firstName() {
        return binding.getFirstName();
    }

    @Override
    public String lastName() {
        return binding.getLastName();
    }

    @Override
    public String address() {
        return binding.getAddress();
    }

    @Override
    public String city() {
        return binding.getCity();
    }

    @Override
    public String state() {
        return stateName;
    }

    @Override
    public String country() {
        return countryName;
    }

    @Override
    public String pin() {
        return binding.getPin();
    }

    @Override
    public String phone() {
        return binding.getPhone();
    }

    @Override
    public boolean isSave() {
        return binding.getIsSave();
    }

    @Override
    public void countriesResponse(CountriesListModel output) {
        countries.clear();
        countries.addAll(output.getCountries());
        adapter.notifyDataSetChanged();
        if (getLocalData().getShippingAddress() != null) {
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).getCode().equals(getLocalData().getShippingAddress().getCountry())) {
                    spCountry.setSelection(i);
                    states.clear();
                    states.addAll(output.getCountries().get(i).getProvinces());
                    statesAdapter.notifyDataSetChanged();
                    for (int j = 0; j < countries.get(i).getProvinces().size(); j++) {
                        if (countries.get(i).getProvinces().get(j).getCode().equals(getLocalData().getShippingAddress().getState())) {
                            final int finalJ = j;
                            new Handler().postDelayed(() -> spState.setSelection(finalJ), 100);
                            break;
                        }
                    }
                }
            }
        } else {
            states.clear();
            states.addAll(output.getCountries().get(0).getProvinces());
            statesAdapter.notifyDataSetChanged();
            new Handler().postDelayed(() -> spState.setSelection(0), 100);
        }
    }
}
