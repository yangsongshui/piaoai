package com.example.yangsong.piaoai.presenter;

/**
 * Created by ys on 2017/6/13.
 */

public interface GetCodePresenter {

    /**
     * @descriptoin	请求验证码
     * @author	ys
     * @param phoneNumber 手机号
     * @param type 请求类型 0 注册 1忘记密码
     * @date 2017/6/13
     * @return
     */
    void GetCode(String phoneNumber, String type);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
