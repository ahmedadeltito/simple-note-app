package com.ahmedadel.noteapp.home.adapter;

import android.support.v7.util.DiffUtil;

import com.ahmedadel.noteapp.model.Note;

import java.util.List;
import java.util.Objects;

/**
 * Created by Ahmed Adel on 3/28/18.
 * DiffUtil is a utility class that can calculate the difference between two lists and output a
 * list of update operations that converts the first list into the second one.
 * NoteDiffCallback used by DiffUtil while calculating the diff between two lists.
 */

public class NoteDiffCallback extends DiffUtil.Callback {

    private final List<Note> oldList;
    private final List<Note> newList;

    NoteDiffCallback(List<Note> oldList, List<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
