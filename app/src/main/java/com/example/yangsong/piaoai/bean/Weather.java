package com.example.yangsong.piaoai.bean;

/**
 * Created by ys on 2017/6/20.
 */

public class Weather {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"f6":{"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1005 hPa","jiangshui":"1%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":7,"night_air_temperature":"27","day_air_temperature":"32","day_wind_direction":"南风","day":"20170625","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风"},"f7":{"day_weather":"多云","night_weather":"中雨","night_weather_code":"08","air_press":"1005 hPa","jiangshui":"7%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":1,"night_air_temperature":"25","day_air_temperature":"32","day_wind_direction":"无持续风向","day":"20170626","night_weather_pic":"http://app1.showapi.com/weather/icon/night/08.png","night_wind_direction":"无持续风向"},"time":"20170620113000","ret_code":0,"cityInfo":{"c6":"guangdong","c5":"广州","c4":"guangzhou","c3":"广州","c9":"中国","c8":"china","c7":"广东","c17":"+8","c16":"AZ9200","c1":"101280101","c2":"guangzhou","longitude":113.265,"c11":"020","c10":"1","latitude":23.108,"c12":"510000","c15":"43"},"now":{"aqiDetail":{"so2":"12","o3":"56","area_code":"guangzhou","pm2_5":"30","primary_pollutant":"","num":"155","co":"0.936","area":"广州","no2":"54","aqi":"49","quality":"优","pm10":"48","o3_8h":"19"},"weather_code":"301","wind_direction":"西南风","temperature_time":"14:31","wind_power":"3级","aqi":"49","sd":"73%","weather_pic":"http://app1.showapi.com/weather/icon/day/301.png","weather":"雨","temperature":"30"},"f1":{"day_weather":"暴雨","night_weather":"大到暴雨","night_weather_code":"23","air_press":"1005 hPa","jiangshui":"95%","night_wind_power":"微风 <5.4m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"10","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/10.png","weekday":2,"night_air_temperature":"25","day_air_temperature":"28","day_wind_direction":"西南风","day":"20170620","night_weather_pic":"http://app1.showapi.com/weather/icon/night/23.png","night_wind_direction":"无持续风向"},"f3":{"day_weather":"雷阵雨","night_weather":"阵雨","night_weather_code":"03","air_press":"1005 hPa","jiangshui":"82%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"04","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","weekday":4,"night_air_temperature":"26","day_air_temperature":"30","day_wind_direction":"无持续风向","day":"20170622","night_weather_pic":"http://app1.showapi.com/weather/icon/night/03.png","night_wind_direction":"南风"},"f2":{"day_weather":"大到暴雨","night_weather":"大雨","night_weather_code":"09","air_press":"1005 hPa","jiangshui":"98%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"23","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/23.png","weekday":3,"night_air_temperature":"25","day_air_temperature":"29","day_wind_direction":"无持续风向","day":"20170621","night_weather_pic":"http://app1.showapi.com/weather/icon/night/09.png","night_wind_direction":"无持续风向"},"f5":{"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1005 hPa","jiangshui":"0%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":6,"night_air_temperature":"27","day_air_temperature":"32","day_wind_direction":"无持续风向","day":"20170624","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风"},"f4":{"day_weather":"阵雨","night_weather":"阵雨","night_weather_code":"03","air_press":"1005 hPa","jiangshui":"77%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"03","sun_begin_end":"05:42|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/03.png","weekday":5,"night_air_temperature":"26","day_air_temperature":"32","day_wind_direction":"东南风","day":"20170623","night_weather_pic":"http://app1.showapi.com/weather/icon/night/03.png","night_wind_direction":"南风"}}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * f6 : {"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1005 hPa","jiangshui":"1%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":7,"night_air_temperature":"27","day_air_temperature":"32","day_wind_direction":"南风","day":"20170625","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风"}
         * f7 : {"day_weather":"多云","night_weather":"中雨","night_weather_code":"08","air_press":"1005 hPa","jiangshui":"7%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":1,"night_air_temperature":"25","day_air_temperature":"32","day_wind_direction":"无持续风向","day":"20170626","night_weather_pic":"http://app1.showapi.com/weather/icon/night/08.png","night_wind_direction":"无持续风向"}
         * time : 20170620113000
         * ret_code : 0
         * cityInfo : {"c6":"guangdong","c5":"广州","c4":"guangzhou","c3":"广州","c9":"中国","c8":"china","c7":"广东","c17":"+8","c16":"AZ9200","c1":"101280101","c2":"guangzhou","longitude":113.265,"c11":"020","c10":"1","latitude":23.108,"c12":"510000","c15":"43"}
         * now : {"aqiDetail":{"so2":"12","o3":"56","area_code":"guangzhou","pm2_5":"30","primary_pollutant":"","num":"155","co":"0.936","area":"广州","no2":"54","aqi":"49","quality":"优","pm10":"48","o3_8h":"19"},"weather_code":"301","wind_direction":"西南风","temperature_time":"14:31","wind_power":"3级","aqi":"49","sd":"73%","weather_pic":"http://app1.showapi.com/weather/icon/day/301.png","weather":"雨","temperature":"30"}
         * f1 : {"day_weather":"暴雨","night_weather":"大到暴雨","night_weather_code":"23","air_press":"1005 hPa","jiangshui":"95%","night_wind_power":"微风 <5.4m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"10","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/10.png","weekday":2,"night_air_temperature":"25","day_air_temperature":"28","day_wind_direction":"西南风","day":"20170620","night_weather_pic":"http://app1.showapi.com/weather/icon/night/23.png","night_wind_direction":"无持续风向"}
         * f3 : {"day_weather":"雷阵雨","night_weather":"阵雨","night_weather_code":"03","air_press":"1005 hPa","jiangshui":"82%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"04","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/04.png","weekday":4,"night_air_temperature":"26","day_air_temperature":"30","day_wind_direction":"无持续风向","day":"20170622","night_weather_pic":"http://app1.showapi.com/weather/icon/night/03.png","night_wind_direction":"南风"}
         * f2 : {"day_weather":"大到暴雨","night_weather":"大雨","night_weather_code":"09","air_press":"1005 hPa","jiangshui":"98%","night_wind_power":"微风 <5.4m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"23","sun_begin_end":"05:42|19:15","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/23.png","weekday":3,"night_air_temperature":"25","day_air_temperature":"29","day_wind_direction":"无持续风向","day":"20170621","night_weather_pic":"http://app1.showapi.com/weather/icon/night/09.png","night_wind_direction":"无持续风向"}
         * f5 : {"day_weather":"多云","night_weather":"多云","night_weather_code":"01","air_press":"1005 hPa","jiangshui":"0%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"微风 <5.4m/s","day_weather_code":"01","sun_begin_end":"05:43|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","weekday":6,"night_air_temperature":"27","day_air_temperature":"32","day_wind_direction":"无持续风向","day":"20170624","night_weather_pic":"http://app1.showapi.com/weather/icon/night/01.png","night_wind_direction":"南风"}
         * f4 : {"day_weather":"阵雨","night_weather":"阵雨","night_weather_code":"03","air_press":"1005 hPa","jiangshui":"77%","night_wind_power":"3-4级 5.5~7.9m/s","day_wind_power":"3-4级 5.5~7.9m/s","day_weather_code":"03","sun_begin_end":"05:42|19:16","ziwaixian":"弱","day_weather_pic":"http://app1.showapi.com/weather/icon/day/03.png","weekday":5,"night_air_temperature":"26","day_air_temperature":"32","day_wind_direction":"东南风","day":"20170623","night_weather_pic":"http://app1.showapi.com/weather/icon/night/03.png","night_wind_direction":"南风"}
         */

        private F6Bean f6;
        private F7Bean f7;
        private String time;
        private int ret_code;
        private CityInfoBean cityInfo;
        private NowBean now;
        private F1Bean f1;
        private F3Bean f3;
        private F2Bean f2;
        private F5Bean f5;
        private F4Bean f4;

        public F6Bean getF6() {
            return f6;
        }

        public void setF6(F6Bean f6) {
            this.f6 = f6;
        }

        public F7Bean getF7() {
            return f7;
        }

        public void setF7(F7Bean f7) {
            this.f7 = f7;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public CityInfoBean getCityInfo() {
            return cityInfo;
        }

        public void setCityInfo(CityInfoBean cityInfo) {
            this.cityInfo = cityInfo;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public F1Bean getF1() {
            return f1;
        }

        public void setF1(F1Bean f1) {
            this.f1 = f1;
        }

        public F3Bean getF3() {
            return f3;
        }

        public void setF3(F3Bean f3) {
            this.f3 = f3;
        }

        public F2Bean getF2() {
            return f2;
        }

        public void setF2(F2Bean f2) {
            this.f2 = f2;
        }

        public F5Bean getF5() {
            return f5;
        }

        public void setF5(F5Bean f5) {
            this.f5 = f5;
        }

        public F4Bean getF4() {
            return f4;
        }



        public void setF4(F4Bean f4) {
            this.f4 = f4;
        }

        public static class F6Bean {
            /**
             * day_weather : 多云
             * night_weather : 多云
             * night_weather_code : 01
             * air_press : 1005 hPa
             * jiangshui : 1%
             * night_wind_power : 3-4级 5.5~7.9m/s
             * day_wind_power : 3-4级 5.5~7.9m/s
             * day_weather_code : 01
             * sun_begin_end : 05:43|19:16
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/01.png
             * weekday : 7
             * night_air_temperature : 27
             * day_air_temperature : 32
             * day_wind_direction : 南风
             * day : 20170625
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/01.png
             * night_wind_direction : 南风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class F7Bean {
            /**
             * day_weather : 多云
             * night_weather : 中雨
             * night_weather_code : 08
             * air_press : 1005 hPa
             * jiangshui : 7%
             * night_wind_power : 微风 <5.4m/s
             * day_wind_power : 微风 <5.4m/s
             * day_weather_code : 01
             * sun_begin_end : 05:43|19:16
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/01.png
             * weekday : 1
             * night_air_temperature : 25
             * day_air_temperature : 32
             * day_wind_direction : 无持续风向
             * day : 20170626
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/08.png
             * night_wind_direction : 无持续风向
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class CityInfoBean {
            /**
             * c6 : guangdong
             * c5 : 广州
             * c4 : guangzhou
             * c3 : 广州
             * c9 : 中国
             * c8 : china
             * c7 : 广东
             * c17 : +8
             * c16 : AZ9200
             * c1 : 101280101
             * c2 : guangzhou
             * longitude : 113.265
             * c11 : 020
             * c10 : 1
             * latitude : 23.108
             * c12 : 510000
             * c15 : 43
             */

            private String c6;
            private String c5;
            private String c4;
            private String c3;
            private String c9;
            private String c8;
            private String c7;
            private String c17;
            private String c16;
            private String c1;
            private String c2;
            private double longitude;
            private String c11;
            private String c10;
            private double latitude;
            private String c12;
            private String c15;

            public String getC6() {
                return c6;
            }

            public void setC6(String c6) {
                this.c6 = c6;
            }

            public String getC5() {
                return c5;
            }

            public void setC5(String c5) {
                this.c5 = c5;
            }

            public String getC4() {
                return c4;
            }

            public void setC4(String c4) {
                this.c4 = c4;
            }

            public String getC3() {
                return c3;
            }

            public void setC3(String c3) {
                this.c3 = c3;
            }

            public String getC9() {
                return c9;
            }

            public void setC9(String c9) {
                this.c9 = c9;
            }

            public String getC8() {
                return c8;
            }

            public void setC8(String c8) {
                this.c8 = c8;
            }

            public String getC7() {
                return c7;
            }

            public void setC7(String c7) {
                this.c7 = c7;
            }

            public String getC17() {
                return c17;
            }

            public void setC17(String c17) {
                this.c17 = c17;
            }

            public String getC16() {
                return c16;
            }

            public void setC16(String c16) {
                this.c16 = c16;
            }

            public String getC1() {
                return c1;
            }

            public void setC1(String c1) {
                this.c1 = c1;
            }

            public String getC2() {
                return c2;
            }

            public void setC2(String c2) {
                this.c2 = c2;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getC11() {
                return c11;
            }

            public void setC11(String c11) {
                this.c11 = c11;
            }

            public String getC10() {
                return c10;
            }

            public void setC10(String c10) {
                this.c10 = c10;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getC12() {
                return c12;
            }

            public void setC12(String c12) {
                this.c12 = c12;
            }

            public String getC15() {
                return c15;
            }

            public void setC15(String c15) {
                this.c15 = c15;
            }
        }

        public static class NowBean {
            /**
             * aqiDetail : {"so2":"12","o3":"56","area_code":"guangzhou","pm2_5":"30","primary_pollutant":"","num":"155","co":"0.936","area":"广州","no2":"54","aqi":"49","quality":"优","pm10":"48","o3_8h":"19"}
             * weather_code : 301
             * wind_direction : 西南风
             * temperature_time : 14:31
             * wind_power : 3级
             * aqi : 49
             * sd : 73%
             * weather_pic : http://app1.showapi.com/weather/icon/day/301.png
             * weather : 雨
             * temperature : 30
             */

            private AqiDetailBean aqiDetail;
            private String weather_code;
            private String wind_direction;
            private String temperature_time;
            private String wind_power;
            private String aqi;
            private String sd;
            private String weather_pic;
            private String weather;
            private String temperature;

            public AqiDetailBean getAqiDetail() {
                return aqiDetail;
            }

            public void setAqiDetail(AqiDetailBean aqiDetail) {
                this.aqiDetail = aqiDetail;
            }

            public String getWeather_code() {
                return weather_code;
            }

            public void setWeather_code(String weather_code) {
                this.weather_code = weather_code;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getTemperature_time() {
                return temperature_time;
            }

            public void setTemperature_time(String temperature_time) {
                this.temperature_time = temperature_time;
            }

            public String getWind_power() {
                return wind_power;
            }

            public void setWind_power(String wind_power) {
                this.wind_power = wind_power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getSd() {
                return sd;
            }

            public void setSd(String sd) {
                this.sd = sd;
            }

            public String getWeather_pic() {
                return weather_pic;
            }

            public void setWeather_pic(String weather_pic) {
                this.weather_pic = weather_pic;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public static class AqiDetailBean {
                /**
                 * so2 : 12
                 * o3 : 56
                 * area_code : guangzhou
                 * pm2_5 : 30
                 * primary_pollutant :
                 * num : 155
                 * co : 0.936
                 * area : 广州
                 * no2 : 54
                 * aqi : 49
                 * quality : 优
                 * pm10 : 48
                 * o3_8h : 19
                 */

                private String so2;
                private String o3;
                private String area_code;
                private String pm2_5;
                private String primary_pollutant;
                private String num;
                private String co;
                private String area;
                private String no2;
                private String aqi;
                private String quality;
                private String pm10;
                private String o3_8h;

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getArea_code() {
                    return area_code;
                }

                public void setArea_code(String area_code) {
                    this.area_code = area_code;
                }

                public String getPm2_5() {
                    return pm2_5;
                }

                public void setPm2_5(String pm2_5) {
                    this.pm2_5 = pm2_5;
                }

                public String getPrimary_pollutant() {
                    return primary_pollutant;
                }

                public void setPrimary_pollutant(String primary_pollutant) {
                    this.primary_pollutant = primary_pollutant;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getO3_8h() {
                    return o3_8h;
                }

                public void setO3_8h(String o3_8h) {
                    this.o3_8h = o3_8h;
                }
            }
        }

        public static class F1Bean {
            /**
             * day_weather : 暴雨
             * night_weather : 大到暴雨
             * night_weather_code : 23
             * air_press : 1005 hPa
             * jiangshui : 95%
             * night_wind_power : 微风 <5.4m/s
             * day_wind_power : 3-4级 5.5~7.9m/s
             * day_weather_code : 10
             * sun_begin_end : 05:42|19:15
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/10.png
             * weekday : 2
             * night_air_temperature : 25
             * day_air_temperature : 28
             * day_wind_direction : 西南风
             * day : 20170620
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/23.png
             * night_wind_direction : 无持续风向
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class F3Bean {
            /**
             * day_weather : 雷阵雨
             * night_weather : 阵雨
             * night_weather_code : 03
             * air_press : 1005 hPa
             * jiangshui : 82%
             * night_wind_power : 3-4级 5.5~7.9m/s
             * day_wind_power : 微风 <5.4m/s
             * day_weather_code : 04
             * sun_begin_end : 05:42|19:15
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/04.png
             * weekday : 4
             * night_air_temperature : 26
             * day_air_temperature : 30
             * day_wind_direction : 无持续风向
             * day : 20170622
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/03.png
             * night_wind_direction : 南风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class F2Bean {
            /**
             * day_weather : 大到暴雨
             * night_weather : 大雨
             * night_weather_code : 09
             * air_press : 1005 hPa
             * jiangshui : 98%
             * night_wind_power : 微风 <5.4m/s
             * day_wind_power : 微风 <5.4m/s
             * day_weather_code : 23
             * sun_begin_end : 05:42|19:15
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/23.png
             * weekday : 3
             * night_air_temperature : 25
             * day_air_temperature : 29
             * day_wind_direction : 无持续风向
             * day : 20170621
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/09.png
             * night_wind_direction : 无持续风向
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class F5Bean {
            /**
             * day_weather : 多云
             * night_weather : 多云
             * night_weather_code : 01
             * air_press : 1005 hPa
             * jiangshui : 0%
             * night_wind_power : 3-4级 5.5~7.9m/s
             * day_wind_power : 微风 <5.4m/s
             * day_weather_code : 01
             * sun_begin_end : 05:43|19:16
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/01.png
             * weekday : 6
             * night_air_temperature : 27
             * day_air_temperature : 32
             * day_wind_direction : 无持续风向
             * day : 20170624
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/01.png
             * night_wind_direction : 南风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }

        public static class F4Bean {
            /**
             * day_weather : 阵雨
             * night_weather : 阵雨
             * night_weather_code : 03
             * air_press : 1005 hPa
             * jiangshui : 77%
             * night_wind_power : 3-4级 5.5~7.9m/s
             * day_wind_power : 3-4级 5.5~7.9m/s
             * day_weather_code : 03
             * sun_begin_end : 05:42|19:16
             * ziwaixian : 弱
             * day_weather_pic : http://app1.showapi.com/weather/icon/day/03.png
             * weekday : 5
             * night_air_temperature : 26
             * day_air_temperature : 32
             * day_wind_direction : 东南风
             * day : 20170623
             * night_weather_pic : http://app1.showapi.com/weather/icon/night/03.png
             * night_wind_direction : 南风
             */

            private String day_weather;
            private String night_weather;
            private String night_weather_code;
            private String air_press;
            private String jiangshui;
            private String night_wind_power;
            private String day_wind_power;
            private String day_weather_code;
            private String sun_begin_end;
            private String ziwaixian;
            private String day_weather_pic;
            private int weekday;
            private String night_air_temperature;
            private String day_air_temperature;
            private String day_wind_direction;
            private String day;
            private String night_weather_pic;
            private String night_wind_direction;

            public String getDay_weather() {
                return day_weather;
            }

            public void setDay_weather(String day_weather) {
                this.day_weather = day_weather;
            }

            public String getNight_weather() {
                return night_weather;
            }

            public void setNight_weather(String night_weather) {
                this.night_weather = night_weather;
            }

            public String getNight_weather_code() {
                return night_weather_code;
            }

            public void setNight_weather_code(String night_weather_code) {
                this.night_weather_code = night_weather_code;
            }

            public String getAir_press() {
                return air_press;
            }

            public void setAir_press(String air_press) {
                this.air_press = air_press;
            }

            public String getJiangshui() {
                return jiangshui;
            }

            public void setJiangshui(String jiangshui) {
                this.jiangshui = jiangshui;
            }

            public String getNight_wind_power() {
                return night_wind_power;
            }

            public void setNight_wind_power(String night_wind_power) {
                this.night_wind_power = night_wind_power;
            }

            public String getDay_wind_power() {
                return day_wind_power;
            }

            public void setDay_wind_power(String day_wind_power) {
                this.day_wind_power = day_wind_power;
            }

            public String getDay_weather_code() {
                return day_weather_code;
            }

            public void setDay_weather_code(String day_weather_code) {
                this.day_weather_code = day_weather_code;
            }

            public String getSun_begin_end() {
                return sun_begin_end;
            }

            public void setSun_begin_end(String sun_begin_end) {
                this.sun_begin_end = sun_begin_end;
            }

            public String getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(String ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public String getDay_weather_pic() {
                return day_weather_pic;
            }

            public void setDay_weather_pic(String day_weather_pic) {
                this.day_weather_pic = day_weather_pic;
            }

            public int getWeekday() {
                return weekday;
            }

            public void setWeekday(int weekday) {
                this.weekday = weekday;
            }

            public String getNight_air_temperature() {
                return night_air_temperature;
            }

            public void setNight_air_temperature(String night_air_temperature) {
                this.night_air_temperature = night_air_temperature;
            }

            public String getDay_air_temperature() {
                return day_air_temperature;
            }

            public void setDay_air_temperature(String day_air_temperature) {
                this.day_air_temperature = day_air_temperature;
            }

            public String getDay_wind_direction() {
                return day_wind_direction;
            }

            public void setDay_wind_direction(String day_wind_direction) {
                this.day_wind_direction = day_wind_direction;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight_weather_pic() {
                return night_weather_pic;
            }

            public void setNight_weather_pic(String night_weather_pic) {
                this.night_weather_pic = night_weather_pic;
            }

            public String getNight_wind_direction() {
                return night_wind_direction;
            }

            public void setNight_wind_direction(String night_wind_direction) {
                this.night_wind_direction = night_wind_direction;
            }
        }
    }

    @Override
    public String toString() {
        return "Weather{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
