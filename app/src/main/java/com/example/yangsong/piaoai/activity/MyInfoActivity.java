package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.myview.CompilePopupWindow;
import com.example.yangsong.piaoai.presenter.UpdatePresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.GetCity;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.yangsong.piaoai.util.AppUtil.bitmap2Bytes;
import static com.example.yangsong.piaoai.util.AppUtil.hasSdcard;
import static com.example.yangsong.piaoai.util.AppUtil.isEmail;

public class MyInfoActivity extends BaseActivity implements MsgView {
    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private static final int CODE_CAMERA_REQUEST = 3;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
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
    //自定义的弹出框类
    CompilePopupWindow menuWindow;
    Bitmap bitmap;
    private TimePickerView timePickerView;
    private OptionsPickerView optionsPickerView;//地区选择
    private GetCity getCity;
    private String photo = "";
    private Toastor toastor;
    private ProgressDialog progressDialog = null;
    private UpdatePresenterImp updatePresenterImp = null;
    User.ResBodyBean resBody;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        initView();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据提交中,请稍后");
        updatePresenterImp = new UpdatePresenterImp(this, this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_toolbar_right, R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_sex_ll, R.id.compile_birthday_ll, R.id.compile_mailbox_ll, R.id.compile_phone_ll, R.id.compile_position_ll, R.id.compile_address_ll, R.id.compile_department_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                //提交修改
                complete();
                break;
            case R.id.compile_pic_ll:
                //点击头像
                menuWindow.showAtLocation(this.findViewById(R.id.activity_my_info), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.compile_name_ll:
                //点击昵称
                showDialog(compileNameTv);
                break;
            case R.id.compile_sex_ll:
//                性别
                showDialog();
                break;
            case R.id.compile_birthday_ll:
//                生日
                timePickerView.show();
                break;
            case R.id.compile_mailbox_ll:
//                邮箱
                showDialog(compileMailboxTv);
                break;
            case R.id.compile_position_ll:
//                职位
                showDialog(compilePositionTv);
                break;
            case R.id.compile_address_ll:
//                地址
                optionsPickerView.show();
                break;
            case R.id.compile_department_ll:
//                部门
                showDialog(compileDepartmentTv);
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.compile_photo_tv:
                    //相册
                    openGallery();
                    break;
                case R.id.compile_camera_tv:
                    //相机
                    openGamera();

                    break;
                default:
                    break;
            }
            menuWindow.dismiss();

        }

    };

    /**
     * 打开相机
     */
    private void openGamera() {
        // 跳转至拍照界面
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);// 打开相册
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT);
    }

    /**
     * 裁剪图片
     */
    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                setImageToHeadView(data);
            }
        } else if (requestCode == CODE_CAMERA_REQUEST) {
            if (hasSdcard()) {
                File tempFile = new File(
                        Environment.getExternalStorageDirectory(),
                        IMAGE_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                toastor.showSingletonToast("没有SDCard!");
            }


        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
            compilePicIv.setImageBitmap(bitmap);
            byte[] bytes = bitmap2Bytes(bitmap);
            photo = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compilePicIv = null;
        if (bitmap != null)
            bitmap.recycle();
    }

    private void showDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyInfoActivity.this);
        builder.setTitle("选择性别");
        builder.setPositiveButton("男", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText("男");
            }
        });
        builder.setNegativeButton("女", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText("女");
            }
        });
        builder.create().show();
    }

    private void initView() {
        resBody = MyApplication.newInstance().getUser().getResBody();

        compilePhoneTv.setText(resBody.getPhoneNumber());
        if (resBody.getCity() != null)
            compileAddressTv.setText(resBody.getCity());
        if (resBody.getBirthday() != null)
            compileBirthdayTv.setText(resBody.getBirthday());
        if (resBody.getEmail() != null)
            compileMailboxTv.setText(resBody.getEmail());
        if (resBody.getNickName() != null)
            compileNameTv.setText(resBody.getNickName());
        if (resBody.getPosition() != null)
            compilePositionTv.setText(resBody.getPosition());
        if (resBody.getPosition() != null)
            compileDepartmentTv.setText(resBody.getDepartment());
        if (resBody.getSex() != null)
            compileSexTv.setText(resBody.getSex());
        getCity = new GetCity(this);
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx;
                //返回的分别是三个级别的选中位置
                String city = getCity.getOptions1Items().get(options1).getPickerViewText();
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    tx = city + "-" + getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                } else {
                    tx = getCity.getOptions1Items().get(options1).getPickerViewText() + "-" +
                            getCity.getOptions2Items().get(options1).get(options2) + "-" +
                            getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                }

                compileAddressTv.setText(tx);
            }
        }).isDialog(false).build();
        optionsPickerView.setPicker(getCity.getOptions1Items(), getCity.getOptions2Items(), getCity.getOptions3Items());//三级选择器
        //实例化SelectPicPopupWindow
        menuWindow = new CompilePopupWindow(this, itemsOnClick);
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                compileBirthdayTv.setText(DateUtil.dateToString(date, DateUtil.LONG_DATE_FORMAT));
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
    }

    private void showDialog(final TextView textView) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyInfoActivity.this);
        final EditText editText = new EditText(this);
        editText.setMaxLines(1);
        alertDialog.setTitle("请输入").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0)
                    return;
                textView.setText(editText.getText().toString());

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog tempDialog = alertDialog.create();
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }

    private void complete() {
        String name = compileNameTv.getText().toString();
        String sex = compileSexTv.getText().toString();
        String address = compileAddressTv.getText().toString();
        String mail = compileMailboxTv.getText().toString();
        String birthday = compileBirthdayTv.getText().toString();
        String position = compilePositionTv.getText().toString();
        String department = compileDepartmentTv.getText().toString();
        String phone = compilePhoneTv.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phone);
        map.put("headPicByte", photo);
        map.put("nickName", name);
        map.put("city", address);
        map.put("sex", sex);
        map.put("birthday", birthday);
        map.put("email", mail);
        map.put("position", position);
        map.put("department", department);
        resBody.setBirthday(birthday);
        resBody.setNickName(name);
        resBody.setCity(address);
        resBody.setSex(sex);
        resBody.setEmail(mail);
        resBody.setPosition(position);
        resBody.setDepartment(department);
        resBody.setHeadPic(photo);
        if (mail.length() > 3)
            if (isEmail(mail))
                updatePresenterImp.updateUser(map);
            else
                toastor.showSingletonToast("邮箱输入不合法");
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(Msg tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            MyApplication.newInstance().getUser().setResBody(resBody);
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接失败");

    }
}
