package com.app.getonlinedeals.Features.BookingOptions;

import java.util.ArrayList;

public class CheckoutResponse {
    private ArrayList<Response> response;

    public ArrayList<Response> getResponse() {
        return response;
    }

    public class Response {
        private String id;
        private String price;
        private String title;
        private String handle;
        private Checkout checkout;
        private boolean phone_required;

        public String getId() {
            return id;
        }

        public String getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }

        public String getHandle() {
            return handle;
        }

        public Checkout getCheckout() {
            return checkout;
        }

        public boolean isPhone_required() {
            return phone_required;
        }
    }

    public class Checkout {
        private String total_tax;
        private String total_price;
        private String subtotal_price;

        public String getTotal_tax() {
            return total_tax;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getSubtotal_price() {
            return subtotal_price;
        }
    }
}
