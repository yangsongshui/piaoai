package com.example.yangsong.piaoai.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ys on 2017/3/11.
 */

public class AppUtil {

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //把bitmap转换成String
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        Log.d("d", "压缩后的大小=" + b.length);//1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486,压缩后的大小=74473
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /***
     * 判断手机格式是否正确
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /***
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void TVOC(Context context, TextView textView, double tvoc) {
        if (tvoc >= 0 && tvoc <= 0.6) {
            textView.setText("良好");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (tvoc > 0.6 && tvoc <= 1.0) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (tvoc > 1.0 && tvoc <= 1.6) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (tvoc > 1.6) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        }
    }

    public static void PM2_5(Context context, TextView textView, int pm) {
        Log.e("PM2_5", " " + pm);
        if (pm >= 0 && pm <= 35) {
            textView.setText("优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (pm > 35 && pm <= 75) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (pm > 75 && pm <= 115) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (pm > 115 && pm <= 150) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (pm > 150 && pm <= 250) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (pm > 250) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void CO2(Context context, TextView textView, int co2) {
        if (co2 >= 0 && co2 <= 700) {
            textView.setText("清新");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (co2 > 700 && co2 <= 1000) {
            textView.setText("较好");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (co2 > 1000 && co2 <= 1500) {
            textView.setText("较浊");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (co2 > 1500) {
            textView.setText("浑浊");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void jiaquan(Context context, TextView textView, double jiaquan) {
        if (jiaquan >= 0 && jiaquan <= 0.03) {
            textView.setText("优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (jiaquan > 0.03 && jiaquan <= 0.1) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (jiaquan > 0.1 && jiaquan <= 0.2) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (jiaquan > 0.1 && jiaquan <= 0.2) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (jiaquan > 0.1 && jiaquan <= 0.2) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (jiaquan > 0.8) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void wendu(Context context, TextView textView, int wendu) {
        if (wendu <= -20) {
            textView.setText("极寒");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_jihan));
        } else if (wendu > -20 && wendu <= 0) {
            textView.setText("寒冷");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_hanleng));
        } else if (wendu > 0 && wendu <= 10) {
            textView.setText("偏寒");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_pianhan));
        } else if (wendu > 10 && wendu <= 15) {
            textView.setText("偏冷");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_pianleng));
        } else if (wendu > 15 && wendu <= 22) {
            textView.setText("天凉");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_ji));
        } else if (wendu >= 23 && wendu <= 28) {
            textView.setText("舒适");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (wendu > 28 && wendu <= 36) {
            textView.setText("炎热");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (wendu > 36 && wendu <= 39) {
            textView.setText("高温");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (wendu > 39) {
            textView.setText("炙热");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        }
    }

    public static void shidu(Context context, TextView textView, int shidu) {
        if (shidu >= 41 && shidu <= 60) {
            textView.setText("正常");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (shidu >= 0 && shidu <= 40) {
            textView.setText("干燥");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (shidu > 60 && shidu <= 100) {
            textView.setText("潮湿");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        }
    }
}
