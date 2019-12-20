package com.example.mangareader.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.data.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChaptersListAdapter extends RecyclerView.Adapter<ChaptersListAdapter.ChapterViewHolder> { //extends ListAdapter<Chapter, ChaptersListAdapter.ChapterViewHolder> { //

    private List<Chapter> chapters = new ArrayList<>();
    private OnItemClickListener myListener;
    private final LayoutInflater mInflater;
    private static final String TAG = "debugging";
    public ChaptersListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ChaptersListAdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_chapter_item, parent, false);
        return new ChapterViewHolder(itemView);
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.bindTo(chapters.get(position));
    }

    @Override
    public int getItemCount() {
        return chapters != null ? chapters.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        myListener = listener;
    }
    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mangaTitle;
        private TextView chapterNumber;
        private CardView cardView;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mangaTitle = itemView.findViewById(R.id.recent_mangaTitle);
            chapterNumber = itemView.findViewById(R.id.recent_mangaNumber);
            cardView = itemView.findViewById(R.id.recent_cardView);
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
        public void bindTo(Chapter current) {
            if(current != null) {
                mangaTitle.setText(current.getMangaTitle());
                chapterNumber.setText(current.getNumber());
            }
        }
    }
}
