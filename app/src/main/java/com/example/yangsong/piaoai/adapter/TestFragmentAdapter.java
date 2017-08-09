package com.example.yangsong.piaoai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.fragment.IonicFragment;
import com.example.yangsong.piaoai.fragment.MainFragment;
import com.example.yangsong.piaoai.fragment.PMFragment;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

public class TestFragmentAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {
    List<Facility.ResBodyBean.ListBean> mList;
    private int mCount;

    public TestFragmentAdapter(FragmentManager fm, List<Facility.ResBodyBean.ListBean> mList) {
        super(fm);
        this.mList = mList;
        mCount = mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("Fragment", "IonicFragment");
        if (mList.get(position).getType().equals("3")) {
            return new IonicFragment(mList.get(position));
        } else if (mList.get(position).getType().equals("1")) {
            return new MainFragment(mList.get(position));
        } else if (mList.get(position).getType().equals("2")) {
            return new PMFragment(mList.get(position));
        }else if (mList.get(position).getType().equals("4")) {
            return new MainFragment(mList.get(position));
        }

        return new IonicFragment(mList.get(position));
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getIconResId(int index) {
        return index;
    }

    public void setCount(List<Facility.ResBodyBean.ListBean> list) {
        this.mList = list;
        mCount = list.size();
        notifyDataSetChanged();
    }

    /*继承FragmentStatePagerAdapter重写改方法 数据和页面可以动态更改*/
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}