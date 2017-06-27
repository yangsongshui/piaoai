package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.myview.CompilePopupWindow;
import com.example.yangsong.piaoai.presenter.UpdatePresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.GetCity;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.yangsong.piaoai.util.AppUtil.bitmapToString;
import static com.example.yangsong.piaoai.util.AppUtil.isEmail;

public class MyInfoActivity extends BaseActivity implements MsgView, TakePhoto.TakeResultListener, InvokeListener {
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
    @BindView(R.id.compile_ll)
    LinearLayout compile_ll;
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
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
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
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            configCompress(takePhoto);
            switch (v.getId()) {
                case R.id.compile_photo_tv:
                    //相册
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    break;
                case R.id.compile_camera_tv:
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                    //相机
                    break;
                default:
                    break;
            }
            menuWindow.dismiss();

        }

    };

    private CropOptions getCropOptions() {


        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(500).setAspectY(500);
        builder.setOutputX(500).setOutputY(500);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    private void configCompress(TakePhoto takePhoto) {

        int maxSize = 1024 * 10;
        int width = 400;
        int height = 400;
        boolean showProgressBar = false;
        boolean enableRawFile = false;
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
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

        if (resBody.getBirthday() != null && resBody.getBirthday().length() > 0)
            compileBirthdayTv.setText(resBody.getBirthday());
        if (resBody.getEmail() != null && resBody.getEmail().length() > 0)
            compileMailboxTv.setText(resBody.getEmail());
        if (resBody.getNickName() != null && resBody.getNickName().length() > 0)
            compileNameTv.setText(resBody.getNickName());
        if (resBody.getRole().equals("0")) {
            compile_ll.setVisibility(View.GONE);
        } else {
            if (resBody.getPosition() != null && resBody.getPosition().length() > 0)
                compilePositionTv.setText(resBody.getPosition());
            if (resBody.getDepartment() != null && resBody.getDepartment().length() > 0)
                compileDepartmentTv.setText(resBody.getDepartment());
            if (resBody.getCity() != null && resBody.getCity().length() > 0)
                compileAddressTv.setText(resBody.getCity());
        }
        if (resBody.getHeadPic() != null && resBody.getHeadPic().length() > 5) {
            if (resBody.getHeadPic().contains("http:")) {
                MyApplication.newInstance().getGlide().load(resBody.getHeadPic()).into(compilePicIv);
            }
        }
        if (resBody.getSex() != null)
            if (resBody.getSex().equals("1"))
                compileSexTv.setText("男");
            else
                compileSexTv.setText("女");
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
        String mail = compileMailboxTv.getText().toString();
        String birthday = compileBirthdayTv.getText().toString();
        String phone = compilePhoneTv.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phone);
        if (photo.length() > 5) {
            resBody.setHeadPic(photo);
            map.put("headPicByte", photo);
        }
        map.put("nickName", name);
        map.put("sex", sex);
        if (birthday.equals("未设置"))
            map.put("birthday", "");
        else
            map.put("birthday", birthday);

        if (!resBody.getRole().equals("0")) {
            String address = compileAddressTv.getText().toString();
            String position = compilePositionTv.getText().toString();
            String department = compileDepartmentTv.getText().toString();
            if (address.length() > 0 && position.length() > 0 && department.length() > 0) {
                map.put("position", position);
                map.put("department", department);
                map.put("city", address);
                resBody.setPosition(position);
                resBody.setDepartment(department);
                resBody.setCity(address);
            } else {
                toastor.showSingletonToast("个人公司地址等信息不能为空");
                return;
            }
        }
        resBody.setBirthday(birthday);
        resBody.setNickName(name);
        resBody.setSex(sex);
        resBody.setEmail(mail);
        if (mail.equals("未设置")) {
            map.put("email", "");
            updatePresenterImp.updateUser(map);
        } else if (isEmail(mail)) {
            map.put("email", mail);
            updatePresenterImp.updateUser(map);
        } else {
            toastor.showSingletonToast("邮箱输入不合法");
            return;
        }

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
        Log.e("loadDataError", throwable.getLocalizedMessage());
        toastor.showSingletonToast("服务器连接失败");

    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(MyInfoActivity.class.getName(), "takeSuccess：" + result.getImage().getCompressPath());
        Glide.with(this).load(new File(result.getImage().getCompressPath())).into(compilePicIv);
        photo = bitmapToString(result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.e(MyInfoActivity.class.getName(), "takeFail:" + msg);

    }

    @Override
    public void takeCancel() {
        Log.i(MyInfoActivity.class.getName(), "操作已取消");
    }
}
