package com.example.yangsong.piaoai.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.util.AppContextUtil;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.SpUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    public User user;

    {

        PlatformConfig.setWeixin("wxc17969721937b08c", "9ed65111189e5491a09f5d001cc7326d");
        PlatformConfig.setQQZone("1106247638", "rpOJlkPcBYCMIUOe");
    }

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
        UMShareAPI.get(this);
        user = new User();
        if (user.getResBody() == null)
            user.setResBody(new User.ResBodyBean());
        AppContextUtil.init(this);
        SpUtils.init(this);
    }

    public RequestManager getGlide() {

        return Glide.with(this);


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
            SpUtils.putString("password", user.getResBody().getPassWord());
            Log.e("------", user.toString());
        }

    }


    public User getUser() {
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        if (IsRemember) {
            String phone = SpUtils.getString("phone", "");
            String password = SpUtils.getString("password", "");
            Log.e("------", phone + " " + password);
            if (phone.equals("") || password.equals(""))
                return null;
            user.getResBody().setPhoneNumber(phone);
            user.getResBody().setPassWord(password);
            return user;
        } else if (user.getResBody() != null) {
            if (user.getResBody().getPhoneNumber() != null)
                return user;
            else
                return null;
        } else {
            return null;
        }

    }

    public void outLogin() {
        user.setResBody(null);

        SpUtils.putString("username", "");
        SpUtils.putString("password", "");
        clearAllActies();

    }


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
