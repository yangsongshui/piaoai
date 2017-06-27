package com.example.yangsong.piaoai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangsong on 2017/5/14.
 */
public class Facility implements Serializable{


    /**
     * resCode : 0
     * resMessage : 查询成功！
     * resBody : {"list":[{"deviceid":"F1:03:00:00:01","deviceName":"桑泰","type":"1","devicePosition":"广东省深圳市梅林路69号天虹商场3F层","pm2.5":"0","tvoc":"2","co2":"2","pm10":"2","dianliang":"2","shidu":"2","jiaquan":"2","switch":"0","num":0},{"deviceid":"F0:01:00:00:01","deviceName":"测试的","type":"2","devicePosition":"广东省汕尾市附城镇城南居委头社三组42号源昇鞋厂","pm2.5":"7","tvoc":"","co2":"","pm10":"","dianliang":"","shidu":"","jiaquan":"","switch":"0","num":0},{"deviceid":"F0:02:00:00:01","deviceName":"1231","type":"3","devicePosition":"广东省汕尾市海丰县附城镇欣乐幼儿园93号","pm2.5":"0","tvoc":"","co2":"","pm10":"","dianliang":"","shidu":"","jiaquan":"","switch":"0","num":0}]}
     */

    private String resCode;
    private String resMessage;
    private ResBodyBean resBody;



    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public ResBodyBean getResBody() {
        return resBody;
    }

    public void setResBody(ResBodyBean resBody) {
        this.resBody = resBody;
    }

    public static class ResBodyBean implements Serializable{
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * deviceid : F1:03:00:00:01
             * deviceName : 桑泰
             * type : 1
             * devicePosition : 广东省深圳市梅林路69号天虹商场3F层
             * pm2.5 : 0
             * tvoc : 2
             * co2 : 2
             * pm10 : 2
             * dianliang : 2
             * shidu : 2
             * jiaquan : 2
             * switch : 0
             * num : 0
             */

            private String deviceid;
            private String deviceName;
            private String type;
            private String devicePosition;
            @SerializedName("pm2.5")
            private String _$Pm25267; // FIXME check this code
            private String tvoc;
            private String co2;
            private String pm10;
            private String dianliang;
            private String shidu;
            private String jiaquan;
            @SerializedName("switch")
            private String switchX;
            private int num;

            public String getDeviceid() {

                return  deviceid.toLowerCase();
            }

            public void setDeviceid(String deviceid) {
                this.deviceid = deviceid.toLowerCase();
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDevicePosition() {
                return devicePosition;
            }

            public void setDevicePosition(String devicePosition) {
                this.devicePosition = devicePosition;
            }

            public String get_$Pm25267() {
                return _$Pm25267;
            }

            public void set_$Pm25267(String _$Pm25267) {
                this._$Pm25267 = _$Pm25267;
            }

            public String getTvoc() {
                return tvoc;
            }

            public void setTvoc(String tvoc) {
                this.tvoc = tvoc;
            }

            public String getCo2() {
                return co2;
            }

            public void setCo2(String co2) {
                this.co2 = co2;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getDianliang() {
                return dianliang;
            }

            public void setDianliang(String dianliang) {
                this.dianliang = dianliang;
            }

            public String getShidu() {
                return shidu;
            }

            public void setShidu(String shidu) {
                this.shidu = shidu;
            }

            public String getJiaquan() {
                return jiaquan;
            }

            public void setJiaquan(String jiaquan) {
                this.jiaquan = jiaquan;
            }

            public String getSwitchX() {
                return switchX;
            }

            public void setSwitchX(String switchX) {
                this.switchX = switchX;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
    }
}
