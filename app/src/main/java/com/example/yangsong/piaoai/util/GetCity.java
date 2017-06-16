package com.example.yangsong.piaoai.util;

import android.content.Context;
import com.example.yangsong.piaoai.bean.PickerViewData;
import com.example.yangsong.piaoai.bean.ProvinceBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by ys on 2017/3/9.
 */

public class GetCity {
    JSONArray mJsonObj;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<PickerViewData>>> options3Items = new ArrayList<>();
    ArrayList<ArrayList<PickerViewData>> options3Items_01;
    ArrayList<String> cities;//   声明存放城市的集合
    ArrayList<PickerViewData> districts;//声明存放区县集合的集合

    Context context;

    public GetCity(Context context) {
        this.context = context;
        initJsonData();
    }

    /**
     * 从文件中读取地址数据
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getResources().getAssets().open("city.json");

            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            mJsonObj = new JSONArray(sb.toString());
           // Log.e("GetCity", mJsonObj.toString());
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //  解析json填充集合
    public void parseJson() {
        //Log.e("GetCity", mJsonObj.toString());
        try {
            //  遍历数据组
            for (int i = 0; i < mJsonObj.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = mJsonObj.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
               // Log.e("GetCity", provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合

                options3Items_01 = new ArrayList<>();
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    districts = new ArrayList<>();//声明存放区县集合的集合
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        districts.add(new PickerViewData(areaName));

                    }
                    options3Items_01.add(districts);

                }
                //  将存放区县集合的集合放入集合
                options1Items.add(new ProvinceBean(i, provinceName, "描述部分", "其他数据"));
                options2Items.add(cities);
                options3Items.add(options3Items_01);
                //  将存放城市的集合放入集合
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProvinceBean> getOptions1Items() {
        return options1Items;
    }

    public ArrayList<ArrayList<String>> getOptions2Items() {
        return options2Items;
    }

    public ArrayList<ArrayList<ArrayList<PickerViewData>>> getOptions3Items() {
        return options3Items;
    }
}
