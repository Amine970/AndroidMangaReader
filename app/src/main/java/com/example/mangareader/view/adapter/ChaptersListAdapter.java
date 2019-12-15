package com.example.mangareader.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.Chapter;
import java.util.ArrayList;
import java.util.List;

public class ChaptersListAdapter extends RecyclerView.Adapter<ChaptersListAdapter.ChapterViewHolder>{

    private List<Chapter> chapters = new ArrayList<>();
    private final LayoutInflater mInflater;
    private static final String TAG = "debugging";
    private DiffUtil.DiffResult mdiffResult;
    public ChaptersListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

     public void setChapters(List<Chapter> newChapters) {

        //Log.i(TAG, "setChapters: dans setchapters ");
         this.chapters = newChapters;
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
            holder.mangaTitle.setText(current.getMangaTitle());
            holder.chapterNumber.setText(current.getNumber());

        }
    }

    @Override
    public int getItemCount() {
        return chapters != null ? chapters.size() : 0;
    }


    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mangaTitle;
        private TextView chapterNumber;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mangaTitle = itemView.findViewById(R.id.recent_mangaTitle);
            chapterNumber = itemView.findViewById(R.id.recent_mangaNumber);

        }
    }
}
