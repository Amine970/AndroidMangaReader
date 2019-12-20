package com.example.mangareader.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mangareader.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

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
            .into(imageView);
        } else {
            Picasso.get()
            .load(imageUrls.get(position))
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

