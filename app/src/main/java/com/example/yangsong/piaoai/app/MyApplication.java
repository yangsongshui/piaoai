package com.example.yangsong.piaoai.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.util.AppContextUtil;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.SpUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    private User user;


    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContextUtil.init(this);
        SpUtils.init(this);
    }


    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity))
            activitiesList.add(activity);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity))
            activitiesList.remove(activity);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }
        /*try {
            System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    }


    public void setUser(User user) {
        this.user = user;
        Log.e("user", this.user.toString());
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        if (IsRemember) {
            SpUtils.putString("phone", user.getResBody().getPhoneNumber());
            SpUtils.putString("password", user.getResBody().getPsw());
        }

    }


    public User getUser() {
        String phone = SpUtils.getString("phone", "");
        String password = SpUtils.getString("password", "");
        Log.e("------", phone + " " + password);
        if (phone.equals("") || password.equals(""))
            return null;
        user.getResBody().setPhoneNumber(phone);
        user.getResBody().setPsw(password);
        return user;
    }
/*
    public void outLogin() {
        user = null;
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("username", "");
        editor.putString("password", "");
        //提交
        editor.commit();
        clearAllActies();

    }*/


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }


}
