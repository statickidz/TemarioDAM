package com.sendacyl.menu.model;

import android.support.v4.app.Fragment;

public class NavItem {
    public static int ITEM = 1;
    public static int SECTION = 2;
    public static int EXTRA = 3;
    public static int TOP = 4;

    private String mText;
    private int mDrawable;
    private String mData;
    private int mType;
    private Class<? extends Fragment> mFragment;

    public NavItem(String text, int drawable, int type, Class<? extends Fragment> fragment, String data) {
        mText = text;
        mDrawable = drawable;
        mFragment = fragment;
        mData = data;
        mType = type;
    }

    public NavItem(String text, int type) {
        mText = text;
        mType = type;
    }



    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(int drawable) {
        mDrawable = drawable;
    }

    public Class<? extends Fragment> getFragment() {
        return mFragment;
    }

    public void setFragment(Class<? extends Fragment> fragment) {
        mFragment = fragment;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
