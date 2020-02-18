package com.app.getonlinedeals.Features.BookingOptions;

import com.app.getonlinedeals.Base.Contract.Viewable;

interface BookingView extends Viewable<BookingPresenter> {
    void shippingCharges(CheckoutResponse output);

    void bookingResponse(String id);
}
