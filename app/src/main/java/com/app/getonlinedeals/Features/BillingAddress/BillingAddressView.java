package com.app.getonlinedeals.Features.BillingAddress;

import com.app.getonlinedeals.Base.Contract.Viewable;
import com.app.getonlinedeals.Features.ShippingAddress.CountriesListModel;

interface BillingAddressView extends Viewable<BillingAddressPresenter> {

    String firstName();

    String lastName();

    String address();

    String city();

    String state();

    String country();

    String pin();

    String phone();

    boolean isSave();

    void countriesResponse(CountriesListModel output);

}
