package com.example.mangareader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>{
    private final LayoutInflater mInflater;
    private List<Manga> mangas; // Cached copy of words

    public MangaListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public void  setMangas(List<Manga> mangas) {
        //Log.i("debugging", "setMangas: new size: " +  mangas.size());
        this.mangas = mangas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MangaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        if(mangas != null) {
            Manga current = mangas.get(position);
            holder.mangaTitle.setText(current.getTitle());
            String baseImageUrl = "https://cdn.mangaeden.com/mangasimg/";
            Picasso.get()
                    .load(baseImageUrl + current.getImage())
                    .placeholder(R.drawable.ic_manga_placeholder)
                    .resize(250, 250)
                    .into(holder.mangaImageView);
        } else {
            holder.mangaTitle.setText("No Title");
        }
    }
    @Override
    public int getItemCount() {
        return mangas != null ? mangas.size() : 0;
    }

    public class MangaViewHolder extends RecyclerView.ViewHolder {
        private final TextView mangaTitle;
        private final ImageView mangaImageView;
        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            mangaTitle = itemView.findViewById(R.id.textView);
            mangaImageView = itemView.findViewById(R.id.mangaImage);
        }
    }
}
