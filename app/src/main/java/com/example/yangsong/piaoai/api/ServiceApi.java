package com.example.yangsong.piaoai.api;

import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.bean.Identify;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.bean.Weather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 描述：retrofit的接口service定义
 */
public interface ServiceApi {
    //注册用户
    @POST("register?")
    Observable<Msg> loadRegister(@QueryMap Map<String, String> map);
    //登陆
    @POST("login?")
    Observable<User> loadLoginInfo(@Query("phoneNumber") String phoneNumber, @Query("passWord") String psw);
    //第三方登陆
    @POST("thirdLogin?")
    Observable<User> loadThirdLogin(@Query("openID") String openID);
    //获取验证码
    @POST("getIdentify?")
    Observable<Identify> getCode(@Query("phoneNumber") String phoneNumber, @Query("type") String type);
    //找回密码
    @POST("updatePwd?")
    Observable<Msg> updatePwd(@QueryMap Map<String, String> map);
    //更新用户信息
    @POST("updateUser?")
    Observable<Msg> updateUser(@QueryMap Map<String, String> map);
    //查询用户信息
    @POST("findUserByphoneNumber?")
    Observable<User> findUserByphoneNumber(@Query("phoneNumber") String phoneNumber);
    //绑定设备
    @POST("binding?")
    Observable<Msg> binding(@QueryMap Map<String, String> map);
    //查询用户绑定的所有设备信息及对应首页数据
    @POST("findUserDevice?")
    Observable<Facility> findUserDevice(@Query("phoneNumber") String phoneNumber);
    //根据设备号查询对应首页数据
    @POST("findUserDevice?")
    Observable<Msg> findDeviceData(@Query("imei") String imei);
    //查询子账户
    @POST("findChildAccount?")
    Observable<Msg> findChildAccount(@Query("phoneNumber") String phoneNumber);
    //删除子账户 updateDevice
    @POST("deleteChildAccount?")
    Observable<Msg> deleteChildAccount(@Query("ids") String ids);
    //修改设备信息 unbundling
    @POST("updateDevice?")
    Observable<Msg> updateDevice(@QueryMap Map<String, String> map);
    //解绑设备
    @POST("unbundling?")
    Observable<Msg> unbundling(@QueryMap Map<String, String> map);
    //反馈
    @POST("addRemark?")
    Observable<Msg> addRemark(@Query("phoneNumber") String phoneNumber,@Query("remark") String remark);
    //查询PM2.5历史数据
    @POST("getHistoryDataByPM2_5?")
    Observable<PMBean> getHistoryDataByPM2_5(@QueryMap Map<String, String> map);
    //查询CO2历史数据
    @POST("getHistoryDataByCO2?")
    Observable<PMBean> getHistoryDataByCO2(@QueryMap Map<String, String> map);
    //查询TVOC历史数据
    @POST("getHistoryDataByTVOC?")
    Observable<PMBean> getHistoryDataByTVOC(@QueryMap Map<String, String> map);
    //查询甲醛历史数据
    @POST("getHistoryDataByJIAQUAN?")
    Observable<PMBean> getHistoryDataByJIAQUAN(@QueryMap Map<String, String> map);
    //天气查询接口
    @POST("9-2?showapi_appid=40725&showapi_sign=af0b4f5fee3e41169842eb6093b693f4")
    Call<Weather> getWeather(@Query("area") String address ,@Query("needMoreDay") String needMoreDay);
    //查询PM2.5时历史数据
    @POST("getHourDataByPM2_5?")
    Observable<PMBean> getHourDataByPM2_5(@QueryMap Map<String, String> map);
    //查询CO2时历史数据
    @POST("getHourDataByCO2?")
    Observable<PMBean> getHourDataByCO2(@QueryMap Map<String, String> map);
    //查询TVOC时历史数据
    @POST("getHourDataByTVOC?")
    Observable<PMBean> getHourDataByTVOC(@QueryMap Map<String, String> map);
    //查询甲醛时历史数据
    @POST("getHourDataByJIAQUAN?")
    Observable<PMBean> getHourDataByJIAQUAN(@QueryMap Map<String, String> map);
}
