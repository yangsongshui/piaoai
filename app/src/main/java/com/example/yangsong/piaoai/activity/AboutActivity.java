package com.example.yangsong.piaoai.activity;

import android.os.Bundle;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;

import butterknife.OnClick;

public class AboutActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
