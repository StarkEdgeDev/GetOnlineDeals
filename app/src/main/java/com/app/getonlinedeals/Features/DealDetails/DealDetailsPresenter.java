package com.app.getonlinedeals.Features.DealDetails;

import com.app.getonlinedeals.Base.BasePresenter;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.WebServices.ApisHelper;
import com.app.getonlinedeals.WebServices.BaseApplication;

import java.util.ArrayList;

class DealDetailsPresenter extends BasePresenter<DealDetailsView> {
    void getData() {
        getView().showLoading("Please wait");
        String url = "products/" + getView().id() + ".json";
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper.class).dealsDetails(url),
                new BaseCallBack<DealDetailsResponse>() {
                    @Override
                    public void onCallBack(DealDetailsResponse output) {
                        getView().hideLoading();
                        getView().dealDetailResponse(output);
                    }
                });
    }

    void addToCart(String variantId, String quantityIs, String image, String name, String price, String color) {
        ArrayList<CartModel> cartModelArrayList = new ArrayList<>(getView().getLocalData().getCartItems());
        CartModel cartModel = new CartModel(getView().id(), variantId, quantityIs, image, name, price, color, false);
        boolean isAdded = false;
        double priceIsS = Double.parseDouble(price) * Integer.parseInt(quantityIs);
        for (int i = 0; i < cartModelArrayList.size(); i++) {
            if (cartModelArrayList.get(i).getVariantId().equals(variantId)) {
                double priceIs = Double.parseDouble(cartModelArrayList.get(i).getPrice()) + priceIsS;
                cartModel.setPrice(priceIs + "");
                int qnt = Integer.parseInt(cartModelArrayList.get(i).getQuantity()) + Integer.parseInt(quantityIs);
                cartModel.setQuantity(qnt + "");
                cartModelArrayList.remove(i);
                cartModelArrayList.add(cartModel);
                isAdded = true;
                break;
            }
        }
        if (!isAdded) {
            cartModel.setPrice(priceIsS + "");
            cartModelArrayList.add(cartModel);
        }
        getView().getLocalData().setCartItems(cartModelArrayList);
    }
}
