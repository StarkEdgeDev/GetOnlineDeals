package com.app.getonlinedeals.WebServices;

public class OrderResponse {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public class Order {
        private String id;
        private String token;

        public String getId() {
            return id;
        }

        public String getToken() {
            return token;
        }
    }
}
