package com.example.yangsong.piaoai.presenter;

/**
 * Created by ys on 2017/6/16.
 */

public interface FacilityPresener {

    /**
     * @descriptoin	请求接口数据
     * @author	ys
     * @param phoneNumber  请求设备手机号
     * @date 2017/6/13
     * @return
     */
    void findUserDevice(String phoneNumber);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
