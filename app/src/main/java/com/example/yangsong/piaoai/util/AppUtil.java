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

    /***
     * 照片转二进制
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
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

    public static void TVOC(Context context, TextView textView, int tvoc) {
        if (tvoc >= 0 && tvoc <= 35) {
            textView.setText("优");
        } else if (tvoc > 35 && tvoc <= 75) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (tvoc > 75 && tvoc <= 115) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (tvoc > 116 && tvoc <= 150) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (tvoc > 151 && tvoc <= 250) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (tvoc > 251 && tvoc <= 500) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }else {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void PM2_5(Context context, TextView textView, int pm) {
        Log.e("PM2_5", " " +pm);
        if (pm >= 0 && pm <= 35) {
            textView.setText("优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (pm > 35 && pm <= 75) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (pm > 75 && pm <= 115) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (pm > 116 && pm <= 150) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (pm > 151 && pm <= 250) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (pm > 251 && pm <= 500) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }else {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void CO2(Context context, TextView textView, int co2) {
        if (co2 >= 0 && co2 <= 485) {
            textView.setText("极优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_ji));
        } else if (co2 >= 486 && co2 <= 600) {
            textView.setText("优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (co2 > 600 && co2 <= 800) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (co2 > 800 && co2 <= 1000) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (co2 > 1000 && co2 <= 1200) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (co2 > 1200 && co2 <= 1500) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (co2 > 1500) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void jiaquan(Context context, TextView textView, int jiaquan) {
        if (jiaquan >= 0 && jiaquan <= 35) {
            textView.setText("优");
            textView.setBackground(context.getResources().getDrawable(R.drawable.point_selected));
        } else if (jiaquan > 35 && jiaquan <= 75) {
            textView.setText("良");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_liang));
        } else if (jiaquan > 75 && jiaquan <= 115) {
            textView.setText("轻度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (jiaquan > 116 && jiaquan <= 150) {
            textView.setText("中度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (jiaquan > 151 && jiaquan <= 250) {
            textView.setText("重度污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_zhong));
        } else if (jiaquan > 251 && jiaquan <= 500) {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }else {
            textView.setText("严重污染");
            textView.setBackground(context.getResources().getDrawable(R.drawable.pm_yanzhong));
        }
    }

    public static void wendu(Context context, TextView textView, int wendu) {
        if ( wendu <=-20) {
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
