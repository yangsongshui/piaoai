package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.UserAdapter;
import com.example.yangsong.piaoai.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {


    @BindView(R.id.user_list)
    ListView userList;
    List<String> mList;
    UserAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add("----");
        mList.add("----");
        mList.add("----");
        adapter = new UserAdapter(mList, this);
        userList.setAdapter(adapter);

    }


    @OnClick({R.id.user_left_iv, R.id.tv_equipment_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_left_iv:
                finish();
                break;
            case R.id.tv_equipment_right:
                break;
        }
    }
}
