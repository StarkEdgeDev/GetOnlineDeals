package com.app.getonlinedeals.Features.DealsList;

import com.app.getonlinedeals.Base.BasePresenter;
import com.app.getonlinedeals.WebServices.ApisHelper;
import com.app.getonlinedeals.WebServices.BaseApplication;

class DealsListPresenter extends BasePresenter<DealsListView> {
    void getDeals() {
        getView().showLoading("Please wait");
        createApiRequest(BaseApplication.getRetrofit().create(ApisHelper .class).dealsList("92758704228",
                "variants,title,image,id"),
                output -> {
                    getView().hideLoading();
                    getView().dealsResponse(output);
                });
    }
}
