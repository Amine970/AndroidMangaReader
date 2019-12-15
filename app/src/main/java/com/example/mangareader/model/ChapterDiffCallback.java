package com.example.mangareader.model;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ChapterDiffCallback extends DiffUtil.Callback {
    private final List<Chapter> oldChapterList;
    private final List<Chapter> newChapterList;

    public ChapterDiffCallback(List<Chapter> oldChapterList, List<Chapter> newChapterList) {
        this.oldChapterList = oldChapterList;
        this.newChapterList = newChapterList;
    }

    @Override
    public int getOldListSize() {
        return oldChapterList == null ? 0 : oldChapterList.size();
    }

    @Override
    public int getNewListSize() {
        return newChapterList  == null ? 0 :  newChapterList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if(oldChapterList == null || newChapterList == null)
            return true;
        return oldChapterList.get(oldItemPosition).getId().equals(newChapterList.get(newItemPosition).getId());
        //return oldChapterList.get(oldItemPosition).getDate() == newChapterList.get(newItemPosition).getDate();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if(oldChapterList == null || newChapterList == null)
            return true;
        final Chapter oldChapter = oldChapterList.get(oldItemPosition);
        final Chapter newChapter = newChapterList.get(newItemPosition);
        return oldChapter.getTitle().equals(newChapter.getTitle());
    }
}
//@Override
//    public int getOldListSize() {
//        return oldChapterList == null ? 0 : oldChapterList.size();
//    }
//
//    @Override
//    public int getNewListSize() {
//        return newChapterList  == null ? 0 :  newChapterList.size();
//    }