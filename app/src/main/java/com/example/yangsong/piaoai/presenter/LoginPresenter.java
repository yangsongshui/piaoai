package com.example.yangsong.piaoai.presenter;

/**
 * Created by ys on 2017/6/13.
 */

public interface LoginPresenter {

    /**
     * @descriptoin	登陆接口
     * @author	ys
     * @param phoneNumber 手机号
     * @param psw 密码
     * @date 2017/6/13
     * @return
     */
    void loadLogin(String phoneNumber,String psw);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
