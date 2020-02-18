package com.app.getonlinedeals;

import android.view.View;

import com.app.getonlinedeals.ProjectUtils.BaseCallBack;

public class OnClickEvent {
    private final BaseCallBack<View> callBack;

    public OnClickEvent(BaseCallBack<View> callBack) {
        this.callBack = callBack;
    }

    public void onClick(View view) {
        callBack.onCallBack(view);
    }

}