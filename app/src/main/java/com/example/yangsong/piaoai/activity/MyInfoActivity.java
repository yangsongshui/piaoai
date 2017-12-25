package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.myview.CompilePopupWindow;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.GetCity;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.yangsong.piaoai.util.AppUtil.bitmapToString;
import static com.example.yangsong.piaoai.util.AppUtil.isEmail;
import static com.example.yangsong.piaoai.util.AppUtil.stringtoBitmap;

public class MyInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
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
    private TimePickerView timePickerView;
    private OptionsPickerView optionsPickerView;//地区选择
    private GetCity getCity;
    private String photo = "";
    private Toastor toastor;
    private ProgressDialog progressDialog = null;

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
        progressDialog.setMessage(getString(R.string.dialog_msg6));
        //  updatePresenterImp = new UpdatePresenterImp(this, this);
    }

    @OnClick({R.id.iv_toolbar_left, R.id.tv_toolbar_right, R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_sex_ll, R.id.compile_birthday_ll, R.id.compile_mailbox_ll, R.id.compile_phone_ll, R.id.compile_position_ll, R.id.compile_address_ll, R.id.compile_department_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.tv_toolbar_right:
                //提交修改
                postAsynHttp();
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
            switch (v.getId()) {
                case R.id.compile_photo_tv:
                    //相册
                    configCompress(takePhoto, 500, 500);
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    break;
                case R.id.compile_camera_tv:
                    configCompress(takePhoto, 500, 500);
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

    private void configCompress(TakePhoto takePhoto, int width, int height) {

        int maxSize = 1024 * 10;

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
        builder.setTitle(getString(R.string.info_msg));
        builder.setPositiveButton(getString(R.string.info_msg2), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText(getString(R.string.info_msg2));
            }
        });
        builder.setNegativeButton(getString(R.string.info_msg3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText(getString(R.string.info_msg3));
            }
        });
        builder.create().show();
    }

    private void initView() {
        resBody = MyApplication.newInstance().getUser().getResBody();
        compilePhoneTv.setText(resBody.getPhoneNumber());
        if (resBody.getPhoneNumber().length() > 11)
            compilePhoneTv.setVisibility(View.INVISIBLE);


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
            } else
                compilePicIv.setImageBitmap(stringtoBitmap(resBody.getHeadPic()));
        }
        if (resBody.getSex() != null)
            if (resBody.getSex().equals("0") || resBody.getSex().equals(getString(R.string.info_msg2)))
                compileSexTv.setText(getString(R.string.info_msg2));
            else
                compileSexTv.setText(getString(R.string.info_msg3));
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
        alertDialog.setTitle(getString(R.string.dialog_msg11)).setView(editText).setPositiveButton(getString(R.string.dialog_bt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0)
                    return;
                textView.setText(editText.getText().toString());

            }
        }).setNegativeButton(getString(R.string.dialog_bt2), new DialogInterface.OnClickListener() {
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


    private void postAsynHttp() {
        FormBody.Builder builder = new FormBody.Builder();
        String name = compileNameTv.getText().toString();
        String sex = compileSexTv.getText().toString();
        String mail = compileMailboxTv.getText().toString();
        String birthday = compileBirthdayTv.getText().toString();
        String phone = compilePhoneTv.getText().toString();

        builder.add("phoneNumber", phone);
        if (photo.length() > 5) {
            resBody.setHeadPic(photo);
            //map.put("headPicByte", photo);
            builder.add("headPicByte", photo);
        }
   
        builder.add("sex", sex)
                .add("nickName", name);
        if (birthday.equals(getString(R.string.info_msg4)))
            builder.add("birthday", "");
        else
            builder.add("birthday", birthday);


        if (!resBody.getRole().equals("0")) {
            String address = compileAddressTv.getText().toString();
            String position = compilePositionTv.getText().toString();
            String department = compileDepartmentTv.getText().toString();
            if (address.length() > 0 && position.length() > 0 && department.length() > 0) {
                builder.add("position", position);
                builder.add("department", department);
                builder.add("city", address);
                resBody.setPosition(position);
                resBody.setDepartment(department);
                resBody.setCity(address);
            } else {
                toastor.showSingletonToast(getString(R.string.dialog_msg12));
                return;
            }
        }
        resBody.setBirthday(birthday);
        resBody.setNickName(name);
        resBody.setSex(sex);
        resBody.setEmail(mail);
        if (mail.equals( getString(R.string.info_msg4))) {

            builder.add("email", "");

        } else if (isEmail(mail)) {

            builder.add("email", mail);

        } else {
            toastor.showSingletonToast(getString(R.string.dialog_msg13));
            return;
        }
        RequestBody formBody = builder.build();
        OkHttpClient mOkHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://47.52.24.148:8080/t_user_app/updateUser?")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toastor.showSingletonToast(getString(R.string.dialog_msg5));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject dataJson = new JSONObject(str);
                            toastor.showSingletonToast(dataJson.optString("resMessage"));
                            if (dataJson.optString("resCode").equals("0")){
                                MyApplication.newInstance().getUser().setResBody(resBody);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
