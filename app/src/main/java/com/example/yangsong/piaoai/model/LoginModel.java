package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;


/**
 * 描述：MVP中的M；登陆
 */
public interface LoginModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @author	Ys
     * @param phoneNumber 登陆账号
     * @param psw 登陆密码
     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void loadLogin(String phoneNumber,String psw, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author Ys
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
