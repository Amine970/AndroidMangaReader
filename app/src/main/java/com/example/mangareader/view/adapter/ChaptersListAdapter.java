package com.example.mangareader.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangareader.R;
import com.example.mangareader.model.Chapter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ChaptersListAdapter extends RecyclerView.Adapter<ChaptersListAdapter.ChapterViewHolder>{

    private List<Chapter> chapters;
    private final LayoutInflater mInflater;
    private static final String TAG = "debugging";

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
            //Log.i(TAG, "onBindViewHolder: title -> " + current.getTitle());
            holder.mangaTitle.setText(current.getMangaTitle());
            Timestamp ts=new Timestamp(current.getDate()*1000);
            Date date=new Date(ts.getTime());
            //System.out.println(date);
            holder.chapterDate.setText(date.toString());
            holder.chapterNumber.setText(String.format("%d",current.getNumber()));
        }
    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "getItemCount: size -> " + (chapters != null ? chapters.size() : 0));
        return chapters != null ? chapters.size() : 0;
    }


    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mangaTitle;
        private TextView chapterDate;
        private TextView chapterNumber;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterDate = itemView.findViewById(R.id.recent_mangaDate);
            mangaTitle = itemView.findViewById(R.id.recent_mangaTitle);
            chapterNumber = itemView.findViewById(R.id.recent_mangaNumber);
        }
    }
}
