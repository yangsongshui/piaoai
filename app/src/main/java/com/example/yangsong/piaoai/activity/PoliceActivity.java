package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.PoliceAdapter;
import com.example.yangsong.piaoai.bean.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PoliceActivity extends BaseActivity {
    @BindView(R.id.police_list_lv)
    ListView policelist;

    List<String> mList;
    PoliceAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_police;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add("111");
        mList.add("111");
        mList.add("111");
        adapter = new PoliceAdapter(mList, this);
        policelist.setAdapter(adapter);
    }

    @OnClick({R.id.police_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.police_left_iv:
                finish();
                break;

        }
    }
}
