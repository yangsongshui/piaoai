package com.example.yangsong.piaoai.bean;

/**
 * 作者：omni20170501
 */

public class YC {

    /**
     * resCode : 0
     * resMessage : 查询成功！
     * resBody : {"map":{"pm1P0":"0","PM10":"0","pm2P5":"0","wendu":"-40.0","shidu":"0.0","daqiya":"0.0","zaosheng":"0.0","wind":"4626.0","windDire":"0","status":"关闭"}}
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
        /**
         * map : {"pm1P0":"0","PM10":"0","pm2P5":"0","wendu":"-40.0","shidu":"0.0","daqiya":"0.0","zaosheng":"0.0","wind":"4626.0","windDire":"0","status":"关闭"}
         */

        private MapBean map;

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public static class MapBean {
            /**
             * pm1P0 : 0
             * PM10 : 0
             * pm2P5 : 0
             * wendu : -40.0
             * shidu : 0.0
             * daqiya : 0.0
             * zaosheng : 0.0
             * wind : 4626.0
             * windDire : 0
             * status : 关闭
             */

            private String pm1P0;
            private String PM10;
            private String pm2P5;
            private String wendu;
            private String shidu;
            private String daqiya;
            private String zaosheng;
            private String wind;
            private String windDire;
            private String status;

            public String getPm1P0() {
                return pm1P0;
            }

            public void setPm1P0(String pm1P0) {
                this.pm1P0 = pm1P0;
            }

            public String getPM10() {
                return PM10;
            }

            public void setPM10(String PM10) {
                this.PM10 = PM10;
            }

            public String getPm2P5() {
                return pm2P5;
            }

            public void setPm2P5(String pm2P5) {
                this.pm2P5 = pm2P5;
            }

            public String getWendu() {
                return wendu;
            }

            public void setWendu(String wendu) {
                this.wendu = wendu;
            }

            public String getShidu() {
                return shidu;
            }

            public void setShidu(String shidu) {
                this.shidu = shidu;
            }

            public String getDaqiya() {
                return daqiya;
            }

            public void setDaqiya(String daqiya) {
                this.daqiya = daqiya;
            }

            public String getZaosheng() {
                return zaosheng;
            }

            public void setZaosheng(String zaosheng) {
                this.zaosheng = zaosheng;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWindDire() {
                return windDire;
            }

            public void setWindDire(String windDire) {
                this.windDire = windDire;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
