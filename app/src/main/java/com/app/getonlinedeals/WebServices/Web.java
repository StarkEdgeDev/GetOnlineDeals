/*
 * Copyright (c) 2017. Code by PRM . Happy coding
 */

package com.app.getonlinedeals.WebServices;

public @interface Web {

    @interface Path {
        String BASE_URL = "https://getonlinedeal.myshopify.com/admin/api/2019-07/";
        String BASE_UR2 = "https://templatesgroup.com/app/";
    }

    @interface Apis {
        String PRODUCTS = "products.json";
        String CHECKOUTS = "checkouts.json";
        String CHECKOUT = "Android/android_data.php";
        String ORDERS = "orders.json";
        String COUNTRIES = "countries.json";
        String PRICE_RULES = "price_rules.json";
    }
}
