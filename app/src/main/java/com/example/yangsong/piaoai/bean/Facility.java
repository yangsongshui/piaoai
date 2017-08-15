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
     * resBody : {"list":[{"deviceid":"F1:03:00:00:01","deviceName":"2222","type":"1","devicePosition":"广东省深圳市宝安区西乡街道铁岗东路4号铁岗大厦4号","tvoc":"22","co2":"854","pm10":"3","dianliang":"100","shidu":"35","jiaquan":"888","pm2.5":"3","switch":"0","num":0},{"deviceid":"f0:01:00:00:01","deviceName":"哈哈哈","type":"2","devicePosition":"广东省深圳市龙岗区南湾街道布澜路佳兆业科技广场18号","tvoc":"","co2":"","pm10":"","dianliang":"","shidu":"","jiaquan":"","pm2.5":"","switch":"0","num":0},{"deviceid":"f1:04:00:00:01","deviceName":"pm10设备","type":"4","devicePosition":"广东省深圳市龙岗区南湾街道深圳市学之友科技有限公司21号","tvoc":"","co2":"","pm10":"","dianliang":"","shidu":"","jiaquan":"","pm2.5":"","switch":"0","num":0}]}
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

    public static class ResBodyBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * deviceid : F1:03:00:00:01
             * deviceName : 2222
             * type : 1
             * devicePosition : 广东省深圳市宝安区西乡街道铁岗东路4号铁岗大厦4号
             * tvoc : 22
             * co2 : 854
             * pm10 : 3
             * dianliang : 100
             * shidu : 35
             * jiaquan : 888
             * pm2.5 : 3
             * switch : 0
             * num : 0
             */

            private String deviceid;
            private String deviceName;
            private String type;
            private String devicePosition;
            private String tvoc;
            private String co2;
            private String pm10;
            private String dianliang;
            private String shidu;
            private String jiaquan;
            @SerializedName("pm2.5")
            private String _$Pm25224; // FIXME check this code
            @SerializedName("switch")
            private String switchX;
            private int num;

            public String getDeviceid() {
                return deviceid;
            }

            public void setDeviceid(String deviceid) {
                this.deviceid = deviceid;
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

            public String get_$Pm25224() {
                return _$Pm25224;
            }

            public void set_$Pm25224(String _$Pm25224) {
                this._$Pm25224 = _$Pm25224;
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
