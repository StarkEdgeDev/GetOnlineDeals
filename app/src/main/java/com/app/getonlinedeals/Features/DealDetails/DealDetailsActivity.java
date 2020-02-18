package com.app.getonlinedeals.Features.DealDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.getonlinedeals.Base.BaseActivity;
import com.app.getonlinedeals.Features.DealsList.DealsResponse;
import com.app.getonlinedeals.Features.MyBag.MyBagActivity;
import com.app.getonlinedeals.OnClickEvent;
import com.app.getonlinedeals.ProjectUtils.BaseCallBack;
import com.app.getonlinedeals.ProjectUtils.BaseUtils;
import com.app.getonlinedeals.R;
import com.app.getonlinedeals.databinding.ActivityDealDetailsBinding;

import java.util.ArrayList;

public class DealDetailsActivity extends BaseActivity<ActivityDealDetailsBinding, DealDetailsPresenter>
        implements DealDetailsView {
    private ArrayList<DealsResponse.Image> imageArrayList = new ArrayList<>();
    private ImagesAdapter imagesAdapter;
    int quantityIs = 1;
    private ArrayList<DealsResponse.Variants> variantsList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private String price;
    private Integer variantPosition;

    public static void start(Activity context, String id, View rc) {
        Intent starter = new Intent(context, DealDetailsActivity.class);
        starter.putExtra("id", id);
        BaseUtils.startTransition(context, starter, rc);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_deal_details;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new DealDetailsPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        findViewById(R.id.ivMenu).setVisibility(View.GONE);
        ImageView icBack = findViewById(R.id.ivBack);
        icBack.setVisibility(View.VISIBLE);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView rvImages = binding.rvImages;
        rvImages.setLayoutManager(new LinearLayoutManager(getActivityG(), RecyclerView.HORIZONTAL, false));
        imagesAdapter = new ImagesAdapter(imageArrayList, new BaseCallBack<String>() {
            @Override
            public void onCallBack(String output) {
                binding.setImage(output);
            }
        });
        rvImages.setAdapter(imagesAdapter);

        Spinner spin = findViewById(R.id.spColors);
        customAdapter = new CustomAdapter(getActivityG(), variantsList, new BaseCallBack<Integer>() {
            @Override
            public void onCallBack(Integer output) {
                variantPosition = output;
                for (int j = 0; j < imageArrayList.size(); j++) {
                    if (variantsList.get(variantPosition).getImage_id() == null) return;
                    if (variantsList.get(variantPosition).getImage_id().equals(imageArrayList.get(j).getId())) {
                        binding.setImage(imageArrayList.get(j).getSrc());
                        break;
                    }
                }
            }
        });
        spin.setAdapter(customAdapter);

        getPresenter().getData();
        OnClickEvent clickEvent = new OnClickEvent(new BaseCallBack<View>() {
            @Override
            public void onCallBack(View view) {
                switch (view.getId()) {
                    case R.id.ivAdd:
                        quantityIs++;
                        binding.setQuantity(quantityIs + "");
                        break;
                    case R.id.ivSubtract:
                        if (quantityIs == 0) {
                            displayError("This item is not in your cart");
                            return;
                        }
                        quantityIs--;
                        binding.setQuantity(quantityIs + "");
                        break;
                    case R.id.btnCart:
                        if (variantsList.get(variantPosition).getInventory_quantity() == null ||
                                Integer.parseInt(variantsList.get(variantPosition).getInventory_quantity()) < quantityIs) {
                            displayError("Not enough items available.");
                            return;
                        }
                        getPresenter().addToCart(variantsList.get(variantPosition).getId(), quantityIs + "",
                                binding.getImage(), binding.getName(), price, variantsList.get(variantPosition).getTitle());
                        MyBagActivity.start(getActivityG());
                        break;
                }
            }
        });
        binding.setHandler(clickEvent);
    }

    @Override
    public Context getActivityG() {
        return DealDetailsActivity.this;
    }

    @Override
    public String id() {
        return getIntent().getStringExtra("id");
    }

    @Override
    public void dealDetailResponse(DealDetailsResponse output) {
        binding.setName(output.getProduct().getTitle());
        binding.setImage(output.getProduct().getImage().getSrc());
        imageArrayList.clear();
        imageArrayList.addAll(output.getProduct().getImages());
        imagesAdapter.notifyDataSetChanged();
        binding.setDiscountedPrice("$" + output.getProduct().getVariants().get(0).getPrice());
        price = output.getProduct().getVariants().get(0).getPrice();
        binding.setPrice("$" + output.getProduct().getVariants().get(0).getCompare_at_price());

        String percent = "0";
        if (output.getProduct().getVariants().get(0).getCompare_at_price() != null) {
            double discPrice = Double.parseDouble(output.getProduct().getVariants().get(0).getCompare_at_price()) -
                    Double.parseDouble(output.getProduct().getVariants().get(0).getPrice());

            double discPercent = discPrice
                    / Double.parseDouble(output.getProduct().getVariants().get(0).getCompare_at_price()) * 100;
            percent = String.valueOf(discPercent).substring(0, 2);
        } else {
            binding.setPrice("$" + "");
        }
        binding.setPercentSaved("YOU SAVE: " + percent + "%");
        binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.setQuantity(quantityIs + "");
        variantsList.clear();
        variantsList.addAll(output.getProduct().getVariants());
        customAdapter.notifyDataSetChanged();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvHtml.setText(Html.fromHtml("<html><body>" + output.getProduct().getBody_html() + "</body></html>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.tvHtml.setText(Html.fromHtml("<html><body>" + output.getProduct().getBody_html() + "</body></html>"));
        }
    }
}
