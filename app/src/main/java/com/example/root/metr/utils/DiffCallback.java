package com.example.root.metr.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

public  class DiffCallback<T> extends DiffUtil.Callback{

    private List<T> oldPersons;
    private List<T> newPersons;

    public DiffCallback(List<T> newPersons, List<T> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
