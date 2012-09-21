package com.jgrocho.dicetracker;

import java.util.HashSet;

public class Rolls {

    private int[] mData;
    private HashSet<OnChangeListener> mListeners;

    public Rolls() {
        this(new int[11]);
    }

    public Rolls(int[] data) {
        mData = data;
        mListeners = new HashSet<OnChangeListener>();
    }

    public int[] getRolls() {
        return mData;
    }

    public void setData(int[] data) {
        mData = data;
        notifySet();
    }

    public int getAt(int idx) {
        return mData[idx];
    }

    public void setAt(int idx, int v) {
        mData[idx] = v;
        notifyChange(idx);
    }

    public void increaseAt(int idx) {
        mData[idx]++;
        notifyChange(idx);
    }

    public void increateAtBy(int idx, int v) {
        mData[idx] += v;
        notifyChange(idx);
    }

    public static interface OnChangeListener {
        public void onChange(int idx);
        public void onSet();
    }

    public void registerListener(OnChangeListener l) {
        mListeners.add(l);
    }

    public void unregisterListener(OnChangeListener l) {
        mListeners.remove(l);
    }

    private void notifySet() {
        for (OnChangeListener l : mListeners)
            l.onSet();
    }

    private void notifyChange(int idx) {
        for (OnChangeListener l : mListeners)
            l.onChange(idx);
    }

}
