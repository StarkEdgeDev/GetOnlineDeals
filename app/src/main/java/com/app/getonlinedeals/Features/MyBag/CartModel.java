package com.app.getonlinedeals.Features.MyBag;

public class CartModel {
    private String variantId, quantity, image, title, price, color, productId;
    private Boolean isFree;

    public CartModel(String productId, String variantId, String quantity, String image, String  title, String price, String color, boolean isFree) {
        this.variantId = variantId;
        this.quantity = quantity;
        this.image = image;
        this.title = title;
        this.price = price;
        this.color = color;
        this.productId = productId;
        this.isFree = isFree;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getVariantId() {
        return variantId;
    }

    public Boolean isFree() {
        return isFree;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }
}
