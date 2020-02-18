package com.app.getonlinedeals.Features.ShippingCharges;

import com.app.getonlinedeals.Base.BasePresenter;
import com.app.getonlinedeals.Features.DealDetails.DealDetailsResponse;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.WebServices.ApisHelper;
import com.app.getonlinedeals.WebServices.BaseApplication;

import java.util.ArrayList;

class DiscountPresenter extends BasePresenter<DiscountView> {
    double totalPrice() {
        ArrayList<CartModel> cart = new ArrayList<>(getView().getLocalData().getCartItems());
        double price = 0.0;
        for (int i = 0; i < cart.size(); i++) {
            price = price + Double.parseDouble(cart.get(i).getPrice());
        }
        return price;
    }

    void getDiscounts() {
        getView().showLoading("");
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper.class).getDiscounts(),
                new BaseCallBack<DiscountResponse>() {
                    @Override
                    public void onCallBack(DiscountResponse output) {
                        getView().hideLoading();
                        getView().onDiscountRes(output);
                    }
                });
    }

    String getEffectivePrice(DiscountResponse.PriceRules priceRules) {
        String price;
        if (priceRules.getValue_type().equals("percentage")) {
            price = "" + (totalPrice() - (totalPrice() / 100 * -Double.parseDouble(priceRules.getValue())));
        } else {
            price = (totalPrice() + Double.parseDouble(priceRules.getValue())) + "";
        }
        return price;
    }

    void implementDiscount(String productId) {
        getView().showLoading("Please wait");
        String url = "products/" + productId + ".json";
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper.class).dealsDetails(url),
                new BaseCallBack<DealDetailsResponse>() {
                    @Override
                    public void onCallBack(DealDetailsResponse output) {
                        getView().hideLoading();
                        getView().productDetails(output);
                    }
                });
    }
}
