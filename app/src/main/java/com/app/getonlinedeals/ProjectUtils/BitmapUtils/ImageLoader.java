package com.app.getonlinedeals.ProjectUtils.BitmapUtils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.app.getonlinedeals.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

public class ImageLoader {
    @BindingAdapter({"imageUrl"})
    public static void setImageRoundSmall(ImageView imageView, String url) {
        if (url == null || url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(R.drawable.no_image_r)
                    .apply(circleCropTransform().override(400))
                    .into(imageView);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.loading_r)
                .apply(circleCropTransform().override(400))
                .into(imageView);
    }

    @BindingAdapter({"imageUrlBig"})
    public static void setImageBig(ImageView imageView, String url) {
        if (url == null || url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(R.drawable.no_media)
                    .apply(new RequestOptions().optionalCenterCrop().diskCacheStrategy(DiskCacheStrategy.ALL).override(600, 600))
                    .into(imageView);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.loading_image)
                .apply(new RequestOptions().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).override(600, 600))
                .into(imageView);
    }

    @BindingAdapter({"imageUrlBigXY"})
    public static void setImageBigXY(ImageView imageView, String url) {
        if (url == null || url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(R.drawable.no_media)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.loading_image)
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }
}
