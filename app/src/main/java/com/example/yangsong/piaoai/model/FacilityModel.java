package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;

/**
 * Created by ys on 2017/6/16.
 */

public interface FacilityModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @author	Ys
     * @param phoneNumber 查询设备账号账号
     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void findUserDevice(String phoneNumber, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author Ys
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
