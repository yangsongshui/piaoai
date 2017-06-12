package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.compile_pic_iv)
    CircleImageView compilePicIv;
    @BindView(R.id.compile_name_tv)
    TextView compileNameTv;
    @BindView(R.id.compile_sex_tv)
    TextView compileSexTv;
    @BindView(R.id.compile_birthday_tv)
    TextView compileBirthdayTv;
    @BindView(R.id.compile_mailbox_tv)
    TextView compileMailboxTv;
    @BindView(R.id.compile_phone_tv)
    TextView compilePhoneTv;
    @BindView(R.id.compile_position_tv)
    TextView compilePositionTv;
    @BindView(R.id.compile_address_tv)
    TextView compileAddressTv;
    @BindView(R.id.compile_department_tv)
    TextView compileDepartmentTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_toolbar_right, R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_sex_ll, R.id.compile_birthday_ll, R.id.compile_mailbox_ll, R.id.compile_phone_ll, R.id.compile_position_ll, R.id.compile_address_ll, R.id.compile_department_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                //提交修改
                break;
            case R.id.compile_pic_ll:
                //点击头像

                break;
            case R.id.compile_name_ll:
                //点击昵称
                break;
            case R.id.compile_sex_ll:
//                性别
                break;
            case R.id.compile_birthday_ll:
//                生日
                break;
            case R.id.compile_mailbox_ll:
//                邮箱
                break;
            case R.id.compile_phone_ll:
//                手机号
                break;
            case R.id.compile_position_ll:
//                职位
                break;
            case R.id.compile_address_ll:
//                地址
                break;
            case R.id.compile_department_ll:
//                部门
                break;
        }
    }
}
