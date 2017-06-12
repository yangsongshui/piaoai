package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.EquipmentAdapter;
import com.example.yangsong.piaoai.bean.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EquipmentActivity extends BaseActivity implements SwipeMenuListView.OnMenuItemClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.listView)
    SwipeMenuListView listView;
    List<String> mList;
    EquipmentAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add("----");
        mList.add("----");
        mList.add("----");
        adapter = new EquipmentAdapter(mList, this);
        listView.setAdapter(adapter);
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(this);
        listView.setOnItemClickListener(this);
    }


    @OnClick({R.id.equipment_left_iv, R.id.tv_equipment_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.equipment_left_iv:
                finish();
                break;
            case R.id.tv_equipment_right:
                startActivity(new Intent(this, BindingActivity.class));
                break;
        }
    }


    /**
     * 侧滑监听
     */
    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(this, IonicActivity.class));
    }

    /*添加侧滑菜单*/
    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            //改名菜单
            SwipeMenuItem alterItem = new SwipeMenuItem(
                    getApplicationContext());
            // 设置背景颜色
            alterItem.setBackground(new ColorDrawable(getResources().getColor(R.color.tan_hide)));
            // 设置宽度
            alterItem.setWidth(dp2px(90));
            // 设置内容
            alterItem.setTitle("绑定设备");
            // 设置字体大小
            alterItem.setTitleSize(14);
            // 字体颜色
            alterItem.setTitleColor(getResources().getColor(R.color.white));
            // 添加菜单
            menu.addMenuItem(alterItem);

            // 删除菜单
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.carmine_pink)));
            // set item width
            deleteItem.setWidth(dp2px(70));
            // 设置内容
            deleteItem.setTitle("删除");
            // 设置字体大小
            deleteItem.setTitleSize(14);
            // 字体颜色
            deleteItem.setTitleColor(getResources().getColor(R.color.white));
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
