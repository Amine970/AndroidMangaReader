package com.example.mangareader.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mangareader.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.transforms.Transform;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> imageUrls;
    private static final String TAG = "debugging";
    private boolean gotGifDrawable = false;
    private GifDrawable gifFromAssets;
    public ViewPagerAdapter(Context context) {
        this.context = context;
        try {
            gifFromAssets = new GifDrawable( context.getResources(), R.drawable.loading2 );
            gotGifDrawable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        if(gotGifDrawable) {
            Picasso.get()
                    .load(imageUrls.get(position))
                    .placeholder(gifFromAssets)  //
                    .error(R.drawable.ic_error)
                    .into(imageView);
        } else {
            Picasso.get()
            .load(imageUrls.get(position))
            .placeholder(R.drawable.loading_svg)
            .error(R.drawable.ic_error)
            .into(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

