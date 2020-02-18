package com.app.getonlinedeals.ProjectUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.getonlinedeals.Features.BillingAddress.AddressModel;
import com.app.getonlinedeals.Features.ShippingCharges.DiscountResponse;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedPrefHelper {
    private static final String PREF_NAME = "FitV1";
    private SharedPreferences.Editor edit;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        gson = new Gson();
    }

    public void setCartItems(ArrayList<CartModel> token) {
        String jsonText = gson.toJson(token);
        edit.putString("contactList", jsonText);
        edit.apply();
    }

    public ArrayList<CartModel> getCartItems() {
        String jsonText = sharedPreferences.getString("contactList", null);
        CartModel[] text = gson.fromJson(jsonText, CartModel[].class);
        if (text == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(text));
        }
    }

    public void saveBillingAddress(AddressModel billingAddress) {
        String jsonText = gson.toJson(billingAddress);
        edit.putString("billingAddress", jsonText);
        edit.apply();
    }

    public AddressModel getBillingAddress() {
        String jsonText = sharedPreferences.getString("billingAddress", null);
        return gson.fromJson(jsonText, AddressModel.class);
    }

    public void saveShippingAddress(AddressModel shippingAddress) {
        String jsonText = gson.toJson(shippingAddress);
        edit.putString("shippingAddress", jsonText);
        edit.apply();
    }

    public AddressModel getShippingAddress() {
        String jsonText = sharedPreferences.getString("shippingAddress", null);
        return gson.fromJson(jsonText, AddressModel.class);
    }

    public void saveDiscount(DiscountResponse.PriceRules priceRules) {
        String jsonText = gson.toJson(priceRules);
        edit.putString("priceRules", jsonText);
        edit.apply();
    }

    public DiscountResponse.PriceRules getDiscount() {
        String jsonText = sharedPreferences.getString("priceRules", null);
        return gson.fromJson(jsonText, DiscountResponse.PriceRules.class);
    }
}
