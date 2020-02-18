package com.app.getonlinedeals.Features.DealDetails;

import com.app.getonlinedeals.Base.Contract.Viewable;

interface DealDetailsView extends Viewable<DealDetailsPresenter> {
    String id();

    void dealDetailResponse(DealDetailsResponse output);
}
