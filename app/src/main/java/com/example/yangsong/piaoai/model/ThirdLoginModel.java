package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;


/**
 * 描述：MVP中的M；登陆
 */
public interface ThirdLoginModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @author	Ys
     * @param openid 第三方登陆

     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void thirdLogin(String openid, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author Ys
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
