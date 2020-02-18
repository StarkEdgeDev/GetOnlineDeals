package com.app.getonlinedeals.Features.BookingOptions;

import com.app.getonlinedeals.Base.BasePresenter;
import com.app.getonlinedeals.Features.BillingAddress.AddressModel;
import com.app.getonlinedeals.Features.ShippingCharges.DiscountResponse;
import com.app.getonlinedeals.Features.MyBag.CartModel;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.WebServices.ApisHelper;
import com.app.getonlinedeals.WebServices.BaseApplication;
import com.app.getonlinedeals.WebServices.OrderResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

class BookingPresenter extends BasePresenter<BookingView> {
    void placeOrders(DiscountResponse.PriceRules discountCode, AddressModel shippingAddress, AddressModel billingAddress) {
        getView().showLoading("Please wait");
        JsonObject jsonObject = new JsonObject();
        JsonObject checkout = new JsonObject();
        JsonArray line_items = new JsonArray();
        ArrayList<CartModel> data = new ArrayList<>(getView().getLocalData().getCartItems());
        for (int i = 0; i < data.size(); i++) {
            JsonObject innerJson = new JsonObject();
            innerJson.addProperty("variant_id", data.get(i).getVariantId());
            innerJson.addProperty("quantity", Integer.parseInt(data.get(i).getQuantity()));
            line_items.add(innerJson);
        }
        if (discountCode != null) {
            JsonArray discount_codes = new JsonArray();
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("code", discountCode.getTitle());
            jsonObject1.addProperty("amount", -Double.parseDouble(discountCode.getValue()));
            jsonObject1.addProperty("type", discountCode.getValue_type());
            discount_codes.add(jsonObject1);
            checkout.add("discount_codes", discount_codes);
        }
        checkout.addProperty("email", shippingAddress.getEmail());
        JsonObject shipping_address = new JsonObject();
        shipping_address.addProperty("first_name", shippingAddress.getFirstName());
        shipping_address.addProperty("last_name", shippingAddress.getLastName());
        shipping_address.addProperty("address1", shippingAddress.getAddress());
        shipping_address.addProperty("city", shippingAddress.getCity());
        shipping_address.addProperty("province_code", shippingAddress.getState());
        shipping_address.addProperty("country_code", shippingAddress.getCountry());
        shipping_address.addProperty("phone", shippingAddress.getPhone());
        shipping_address.addProperty("zip", shippingAddress.getPin());
        checkout.add("shipping_address", shipping_address);
        JsonObject billing_address = new JsonObject();
        billing_address.addProperty("first_name", billingAddress.getFirstName());
        billing_address.addProperty("last_name", billingAddress.getLastName());
        billing_address.addProperty("address1", billingAddress.getAddress());
        billing_address.addProperty("city", billingAddress.getCity());
        billing_address.addProperty("province_code", billingAddress.getState());
        billing_address.addProperty("country_code", billingAddress.getCountry());
        billing_address.addProperty("country", billingAddress.getCountry());
        billing_address.addProperty("phone", billingAddress.getPhone());
        billing_address.addProperty("zip", billingAddress.getPin());
        checkout.add("billing_address", billing_address);
        checkout.add("line_items", line_items);
        jsonObject.add("order", checkout);
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper.class).placeOrder(jsonObject),
                new BaseCallBack<OrderResponse>() {
                    @Override
                    public void onCallBack(OrderResponse output) {
                        getView().hideLoading();
                        getView().bookingResponse(output.getOrder().getId());
                    }
                });
    }

    void checkOut(String discountCode, AddressModel shippingAddress, AddressModel billingAddress) {
        getView().showLoading("");
        JsonObject jsonObject = new JsonObject();
        JsonObject checkout = new JsonObject();
        JsonArray line_items = new JsonArray();
        ArrayList<CartModel> data = new ArrayList<>(getView().getLocalData().getCartItems());
        for (int i = 0; i < data.size(); i++) {
            JsonObject innerJson = new JsonObject();
            innerJson.addProperty("variant_id", data.get(i).getVariantId());
            innerJson.addProperty("quantity", Integer.parseInt(data.get(i).getQuantity()));
            line_items.add(innerJson);
        }
        if (discountCode != null) checkout.addProperty("discount_code", discountCode);
        checkout.addProperty("email", shippingAddress.getEmail());
        JsonObject shipping_address = new JsonObject();
        shipping_address.addProperty("first_name", shippingAddress.getFirstName());
        shipping_address.addProperty("last_name", shippingAddress.getLastName());
        shipping_address.addProperty("address1", shippingAddress.getAddress());
        shipping_address.addProperty("city", shippingAddress.getCity());
        shipping_address.addProperty("province_code", shippingAddress.getState());
        shipping_address.addProperty("country_code", shippingAddress.getCountry());
        shipping_address.addProperty("phone", shippingAddress.getPhone());
        shipping_address.addProperty("zip", shippingAddress.getPin());
        checkout.add("shipping_address", shipping_address);
        JsonObject billing_address = new JsonObject();
        billing_address.addProperty("first_name", billingAddress.getFirstName());
        billing_address.addProperty("last_name", billingAddress.getLastName());
        billing_address.addProperty("address1", billingAddress.getAddress());
        billing_address.addProperty("city", billingAddress.getCity());
        billing_address.addProperty("province_code", billingAddress.getState());
        billing_address.addProperty("country_code", billingAddress.getCountry());
        billing_address.addProperty("country", billingAddress.getCountry());
        billing_address.addProperty("phone", billingAddress.getPhone());
        billing_address.addProperty("zip", billingAddress.getPin());
        checkout.add("billing_address", billing_address);
        checkout.add("line_items", line_items);
        jsonObject.add("checkout", checkout);
        createApiRequest(BaseApplication.getRetrofit2().create(ApisHelper.class).addToCheckout(jsonObject),
                new BaseCallBack<CheckoutResponse>() {
                    @Override
                    public void onCallBack(CheckoutResponse output) {
                        getView().hideLoading();
                        getView().shippingCharges(output);
                    }
                });
    }
}
