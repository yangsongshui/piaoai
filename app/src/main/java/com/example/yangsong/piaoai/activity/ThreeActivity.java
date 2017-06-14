package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ThreeActivity extends BaseActivity {

    @BindView(R.id.three_name_et)
    EditText threeNameEt;
    @BindView(R.id.three_address_et)
    EditText threeAddressEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_three;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.three_left_iv, R.id.three_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.three_left_iv:
                finish();
                break;
            case R.id.three_tv:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}
