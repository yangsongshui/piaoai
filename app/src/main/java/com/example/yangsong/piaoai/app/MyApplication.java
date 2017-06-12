package com.example.yangsong.piaoai.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.android.volley.RequestQueue;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.SimpleImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.yangsong.piaoai.util.NetworkRequests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合

    private RequestQueue mQueue;



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
        mQueue = Volley.newRequestQueue(this);
        DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(getApplicationContext(), "CacheDirectory");
        cacheParams.setMemCacheSizePercent(0.5f);
        mImageLoader = new SimpleImageLoader(getApplicationContext(), cacheParams);

         NetworkRequests.getInstance().initViw(this).getInstance().init(instance);


    }


    public RequestQueue getmQueue() {
        return mQueue;
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
/*

    public void setUser(User user) {
        this.user = user;
        Log.e("user", this.user.toString());

        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        //设置参数
        editor.putString("username", user.getPhone());
        editor.putString("password", user.getPsw());

        //提交
        editor.commit();

    }


    public User getUser() {

        //获取SharedPreferences对象
        SharedPreferences sharedPre = this.getSharedPreferences("config", this.MODE_PRIVATE);
        String username = sharedPre.getString("username", "");
        String password = sharedPre.getString("password", "");
        Log.e("------", username + " " + password);
        if (username.equals("") || password.equals(""))
            return null;
        user.setPhone(username);
        user.setPsw(password);
        return user;
    }

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

    private SimpleImageLoader mImageLoader;



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
