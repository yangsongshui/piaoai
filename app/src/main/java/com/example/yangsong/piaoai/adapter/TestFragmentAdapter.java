package com.example.yangsong.piaoai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yangsong.piaoai.fragment.MainFragment;
import com.example.yangsong.piaoai.bean.Facility;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    List<Facility> mList;
    private int mCount;

    public TestFragmentAdapter(FragmentManager fm, List<Facility> mList) {
        super(fm);
        this.mList = mList;
        mCount = mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return new MainFragment(mList.get(position % mList.size()));
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getIconResId(int index) {
        return 0;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}