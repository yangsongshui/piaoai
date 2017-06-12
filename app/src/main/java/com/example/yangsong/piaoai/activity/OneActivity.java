package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.OnClick;

public class OneActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_one;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }



    @OnClick({R.id.deploy_left_iv, R.id.one_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deploy_left_iv:
                finish();
                break;
            case R.id.one_tv:
                startActivity(new Intent(this,DeployActivity.class));
                break;
        }
    }
}
