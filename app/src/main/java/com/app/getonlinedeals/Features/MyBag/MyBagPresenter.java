package com.app.getonlinedeals.Features.MyBag;

import com.app.getonlinedeals.Base.BasePresenter;

import java.util.ArrayList;

class MyBagPresenter extends BasePresenter<MyBagView> {
    double totalPrice() {
        ArrayList<CartModel> cart = new ArrayList<>(getView().getLocalData().getCartItems());
        double price = 0.0;
        for (int i = 0; i < cart.size(); i++) {
            price = price + Double.parseDouble(cart.get(i).getPrice());
        }
        return price;
    }

    String realPrice(String price, String quantity, String newQuantity) {
        double priceIs = Double.parseDouble(price);
        int quantityIs = Integer.parseInt(quantity);
        int newQuantityIs = Integer.parseInt(newQuantity);
        double newPrice = priceIs / quantityIs * newQuantityIs;
        return newPrice + "";
    }
}
