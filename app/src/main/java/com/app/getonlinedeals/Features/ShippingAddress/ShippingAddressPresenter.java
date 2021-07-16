package com.app.getonlinedeals.Features.ShippingAddress;

import com.app.getonlinedeals.Base.BasePresenter;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.ProjectUtils.BaseUtils;
import com.app.getonlinedeals.WebServices.ApisHelper;
import com.app.getonlinedeals.WebServices.BaseApplication;

import java.util.ArrayList;

class ShippingAddressPresenter extends BasePresenter<ShippingAddressView> {
    boolean isChecked() {
        boolean isChecked = false;
        if (getView().email() == null || getView().email().isEmpty()) {
            getView().displayError("Please enter email");
        } else if (!BaseUtils.isValidEmail(getView().email())) {
            getView().displayError("Please enter valid email");
        } else if (getView().firstName() == null || getView().firstName().isEmpty()) {
            getView().displayError("Please enter first name");
        } else if (getView().lastName() == null || getView().lastName().isEmpty()) {
            getView().displayError("Please enter last name");
        } else if (getView().address() == null || getView().address().isEmpty()) {
            getView().displayError("Please enter address");
        } else if (getView().city() == null || getView().city().isEmpty()) {
            getView().displayError("Please enter City");
        } else if (getView().state() == null || getView().state().isEmpty()) {
            getView().displayError("Please enter State");
        } else if (getView().country() == null || getView().country().isEmpty()) {
            getView().displayError("Please enter Country");
        } else if (getView().pin() == null || getView().pin().isEmpty()) {
            getView().displayError("Please enter PIN Code");
        } else if (getView().phone() == null || getView().phone().isEmpty()) {
            getView().displayError("Please enter Phone");
        } else {
            isChecked = true;
        }
        return isChecked;
    }

    double totalPrice() {
        ArrayList<CartModel> cart = new ArrayList<>(getView().getLocalData().getCartItems());
        double price = 0.0;
        for (int i = 0; i < cart.size(); i++) {
            price = price + Double.parseDouble(cart.get(i).getPrice());
        }
        return price;
    }

    void getCountries() {
        getView().showLoading("Please wait");
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper.class).getCountries(),
                output -> {
                    getView().hideLoading();
                    getView().countriesResponse(output);
                });
    }
}
