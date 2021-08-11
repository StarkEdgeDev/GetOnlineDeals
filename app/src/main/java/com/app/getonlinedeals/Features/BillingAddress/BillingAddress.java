package com.app.getonlinedeals.Features.BillingAddress;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.BookingOptions.Booking;
import com.app.getonlinedeals.Features.ShippingAddress.CountriesAdapter;
import com.app.getonlinedeals.Features.ShippingAddress.CountriesListModel;
import com.app.getonlinedeals.Features.ShippingAddress.StatesAdapter;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityBillingAddressBinding;

import java.util.ArrayList;

public class BillingAddress extends BaseActivity<ActivityBillingAddressBinding, BillingAddressPresenter>
        implements BillingAddressView {
    ArrayList<CountriesListModel.Countries> countries = new ArrayList<>();
    ArrayList<CountriesListModel.Provinces> states = new ArrayList<>();
    private Spinner spCountry, spState;
    private String countryName, stateName;
    private CountriesAdapter countriesAdapter;
    private StatesAdapter statesAdapter;

    public static void start(Context context, String discountCode, AddressModel addressModel) {
        Intent starter = new Intent(context, BillingAddress.class);
        starter.putExtra("addressModel", addressModel);
        starter.putExtra("discountCode", discountCode);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_billing_address;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new BillingAddressPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(view -> finish());

        final AddressModel shippingAddress = (AddressModel) getIntent().getSerializableExtra("addressModel");

        getPresenter().getCOuntries();
        spCountry = binding.spCountry;
        spState = binding.spState;
        countriesAdapter = new CountriesAdapter(getActivityG(), countries, output -> {
            countryName = countries.get(output).getCode();
            states.clear();
            states.addAll(countries.get(output).getProvinces());
            statesAdapter.notifyDataSetChanged();
        });
        statesAdapter = new StatesAdapter(getActivityG(), states, output -> stateName = states.get(output).getCode());
        spCountry.setAdapter(countriesAdapter);
        spState.setAdapter(statesAdapter);

        binding.setIsSave(false);

        if (getLocalData().getBillingAddress() != null) {
            AddressModel data = getLocalData().getBillingAddress();
            binding.setAddress(data.getAddress());
            binding.setIsSave(true);
            binding.setFirstName(data.getFirstName());
            binding.setLastName(data.getLastName());
            binding.setPhone(data.getPhone());
            binding.setPin(data.getPin());
            binding.setCity(data.getCity());
        }

        final String discountCode = getIntent().getStringExtra("discountCode");

        binding.btnAddShipping.setOnClickListener(view -> {
            if (getPresenter().isChecked()) {
                AddressModel addressModel = new AddressModel(city(), address(), lastName(), firstName(), "",
                        state(), country(), pin(), phone());
                if (binding.getIsSave()) {
                    getLocalData().saveBillingAddress(addressModel);
                }
                Booking.start(getActivityG(), discountCode, addressModel, shippingAddress);
            }
        });
    }

    @Override
    public void countriesResponse(CountriesListModel output) {
        countries.clear();
        countries.addAll(output.getCountries());
        countriesAdapter.notifyDataSetChanged();
        if (getLocalData().getBillingAddress() != null) {
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).getCode().equals(getLocalData().getBillingAddress().getCountry())) {
                    spCountry.setSelection(i);
                    states.clear();
                    states.addAll(output.getCountries().get(i).getProvinces());
                    statesAdapter.notifyDataSetChanged();
                    for (int j = 0; j < countries.get(i).getProvinces().size(); j++) {
                        if (countries.get(i).getProvinces().get(j).getCode().equals(getLocalData().getBillingAddress().getState())) {
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

    @Override
    public Context getActivityG() {
        return BillingAddress.this;
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
}
