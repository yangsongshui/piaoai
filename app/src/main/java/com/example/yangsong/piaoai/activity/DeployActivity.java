package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class DeployActivity extends BaseActivity {


    @BindView(R.id.deploy_et)
    EditText deployEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_deploy;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.deploy_left_iv, R.id.binding_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deploy_left_iv:
                finish();
                break;
            case R.id.binding_tv:
                startActivity(new Intent(this, ThreeActivity.class));
                break;
        }
    }
}
