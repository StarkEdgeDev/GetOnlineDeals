package com.app.getonlinedeals.Features.DealsList;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.DealDetails.DealDetailsActivity;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityDealsListBinding;

import java.util.ArrayList;

public class DealsListActivity extends BaseActivity<ActivityDealsListBinding, DealsListPresenter> implements DealsListView {
    private ArrayList dealList;
    private DealsListAdapter adapter;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_deals_list;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new DealsListPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        dealList = new ArrayList<DealsResponse.Products>();
        RecyclerView rvDeals = binding.rvDeals;
        rvDeals.setLayoutManager(new LinearLayoutManager(getActivityG()));
        adapter = new DealsListAdapter(dealList, id -> DealDetailsActivity.start((Activity) getActivityG(), id, binding.rvDeals));
        rvDeals.setAdapter(adapter);
        getPresenter().getDeals();
    }

    @Override
    public Context getActivityG() {
        return DealsListActivity.this;
    }

    @Override
    public void dealsResponse(DealsResponse output) {
        dealList.clear();
        dealList.addAll(output.getProducts());
        adapter.notifyDataSetChanged();
    }
}
