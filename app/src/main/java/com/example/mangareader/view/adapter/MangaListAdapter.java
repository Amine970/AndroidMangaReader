package com.example.mangareader.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.data.Manga;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.MangaViewHolder> implements Filterable {
    private final LayoutInflater mInflater;
    private List<Manga> mangas; // Cached copy of mangas
    private List<Manga> mangasFull;
    private OnItemClickListener myListener;
    private static final String TAG = "debugging";
    public MangaListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public void  setMangas(List<Manga> mangas) {
        this.mangas = mangas;
        mangasFull = new ArrayList<>(this.mangas);
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

    @Override
    public Filter getFilter() {
        return mangaFilter;
    }
    private Filter mangaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Manga> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
                filteredList.addAll(mangasFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                filteredList.addAll(
                        mangasFull.stream()
                                .filter(manga -> manga.getTitle().toLowerCase().contains(filterPattern)).collect(Collectors.toList()));
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mangas.clear();
            mangas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }
    public class MangaViewHolder extends RecyclerView.ViewHolder {
        private final TextView mangaTitleView;
        private final ImageView mangaImageView;
        private final MaterialCardView cardView;
        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            mangaImageView = itemView.findViewById(R.id.mangaImage);
            mangaTitleView = itemView.findViewById(R.id.all_mangaTitle);
            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            myListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
