package com.example.mangareader.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.Chapter;

import java.util.List;

public class ChaptersListAdapter extends RecyclerView.Adapter<ChaptersListAdapter.ChapterViewHolder>{

    private List<Chapter> chapters;
    private final LayoutInflater mInflater;
    public ChaptersListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChaptersListAdapter.ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_recent_item, parent, false);
        return new ChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChaptersListAdapter.ChapterViewHolder holder, int position) {
        if(chapters != null) {
            Chapter current = chapters.get(position);
            holder.chapterTitle.setText(current.getTitle());
            holder.chapterDate.setText("date place holder");
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView chapterTitle;
        private TextView chapterDate;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterDate = itemView.findViewById(R.id.recent_mangaDate);
            chapterTitle = itemView.findViewById(R.id.recent_mangaTitle);
        }
    }
}
