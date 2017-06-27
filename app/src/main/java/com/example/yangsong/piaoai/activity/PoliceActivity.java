package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.PoliceAdapter;
import com.example.yangsong.piaoai.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PoliceActivity extends BaseActivity {
    @BindView(R.id.police_list_lv)
    ListView policelist;
    @BindView(R.id.write_ll)
    LinearLayout write_ll;
    List<String> mList;
    PoliceAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_police;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();

        if (mList.size() > 0) {
            policelist.setVisibility(View.VISIBLE);
            adapter = new PoliceAdapter(mList, this);
            policelist.setAdapter(adapter);
            write_ll.setVisibility(View.GONE);
        } else {
            policelist.setVisibility(View.GONE);
            write_ll.setVisibility(View.VISIBLE);
        }
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
