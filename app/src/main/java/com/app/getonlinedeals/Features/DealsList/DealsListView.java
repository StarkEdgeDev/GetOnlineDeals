package com.app.getonlinedeals.Features.DealsList;

import com.app.getonlinedeals.Base.Contract.Viewable;

interface DealsListView extends Viewable<DealsListPresenter> {
    void dealsResponse(DealsResponse output);
}
