package com.example.mangareader.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>{
    private final LayoutInflater mInflater;
    private List<Manga> mangas; // Cached copy of words
    private static final String TAG = "debugging";
    public MangaListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public void  setMangas(List<Manga> mangas) {
        this.mangas = mangas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_allmangas_item, parent, false);
        return new MangaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        if(mangas != null) {
            Manga current = mangas.get(position);
            String title = current.getTitle();
            holder.mangaTitleView.setText(title.length() > 10 ? title.substring(0,10)+"..." : title);
            Picasso.get()
                    .load(current.getImage())
                    .placeholder(R.drawable.ic_manga_placeholder)
                    .fit()
                    .into(holder.mangaImageView);
        }
    }
    @Override
    public int getItemCount() {
        return mangas != null ? mangas.size() : 0;
    }

    public class MangaViewHolder extends RecyclerView.ViewHolder {
        private final TextView mangaTitleView;
        private final ImageView mangaImageView;
        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            mangaImageView = itemView.findViewById(R.id.mangaImage);
            mangaTitleView = itemView.findViewById(R.id.all_mangaTitle);
        }
    }
}
