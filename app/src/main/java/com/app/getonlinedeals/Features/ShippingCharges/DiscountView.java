package com.app.getonlinedeals.Features.ShippingCharges;

import com.app.getonlinedeals.Base.Contract.Viewable;
import com.app.getonlinedeals.Features.DealDetails.DealDetailsResponse;

interface DiscountView extends Viewable<DiscountPresenter> {
    void onDiscountRes(DiscountResponse output);

    void productDetails(DealDetailsResponse output);
}
