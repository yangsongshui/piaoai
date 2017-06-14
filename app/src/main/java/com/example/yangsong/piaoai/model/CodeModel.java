package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;

/**
 * Created by ys on 2017/6/13.
 */

public interface CodeModel<T> {
    /**
     * @param phoneNumber          接收验证码手机号
     * @param type    请求类型 0注册 1忘记密码
     * @param iBaseRequestCallBack 数据的回调接口
     * @descriptoin 获取验证码
     * @author Ys
     * @date 2017/6/13
     */
    void getCode(String phoneNumber,String type, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @param
     * @return
     * @descriptoin 注销subscribe
     * @author Ys
     * @date 2017/6/13
     */
    void onUnsubscribe();
}
