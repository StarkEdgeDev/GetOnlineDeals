package com.app.getonlinedeals.Features.ShippingAddress;

import com.app.getonlinedeals.Base.Contract.Viewable;

interface ShippingAddressView extends Viewable<ShippingAddressPresenter> {
    String email();

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
