/*
 * Copyright (c) 2017. Code by mohit . Happy coding
 */

package com.app.getonlinedeals.WebServices;

import com.app.getonlinedeals.Features.BookingOptions.CheckoutResponse;
import com.app.getonlinedeals.Features.DealDetails.DealDetailsResponse;
import com.app.getonlinedeals.Features.DealsList.DealsResponse;
import com.app.getonlinedeals.Features.ShippingCharges.DiscountResponse;
import com.app.getonlinedeals.Features.ShippingAddress.CountriesListModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApisHelper {
    @GET(Web.Apis.PRODUCTS)
    Observable<DealsResponse> dealsList(@Query("collection_id") String collection_id,
                                        @Query("fields") String fields);

    @GET
    Observable<DealDetailsResponse> dealsDetails(@Url String url);

    @POST(Web.Apis.CHECKOUTS)
    Observable<CheckoutResponse> addToCheckouts(@Body JsonObject url);

    @POST(Web.Apis.CHECKOUT)
    Observable<CheckoutResponse> addToCheckout(@Body JsonObject url);

    @POST(Web.Apis.ORDERS)
    Observable<OrderResponse> placeOrder(@Body JsonObject url);

    @GET(Web.Apis.PRODUCTS)
    Observable<DealsResponse> dealsList(@Query("collection_id") String collection_id,
                                        @Query("limit") String limit,
                                        @Query("fields") String fields);

    @GET(Web.Apis.COUNTRIES)
    Observable<CountriesListModel> getCountries();

    @GET(Web.Apis.PRICE_RULES)
    Observable<DiscountResponse> getDiscounts();
}
