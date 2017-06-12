package com.example.yangsong.piaoai.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.inter.JsonDataReturnListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by yangsong on 2017/3/16.
 */

public class NetworkRequests {

    private static ProgressDialog progressDialog;

    private static Handler handler;
    private static NetworkRequests instance;
    private static Runnable myRunnable;
    private Context appContext;
    public Boolean isShow = true;


    public synchronized static NetworkRequests getInstance() {
        if (instance == null) {
            instance = new NetworkRequests();
        }
        return instance;
    }

    public void init(Context context) {

        appContext = context;
        handler = new Handler();
        myRunnable = new Runnable() {
            @Override
            public void run() {

                if (progressDialog.isShowing() && !isShow) {
                    isShow = true;
                    Log.e("progressDialog", "dismiss");
                    progressDialog.dismiss();
                }


            }
        };


    }

    public void GetRequests(final String url, final Map<String, String> map, final JsonDataReturnListener jsonListener) {
        Log.e("GetRequests", url);
        if (!progressDialog.isShowing() && isShow) {
            isShow = false;
            progressDialog.show();
            Log.e("progressDialog", "show");
            handler.postDelayed(myRunnable, 5000);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue mQueue = MyApplication.newInstance().getmQueue();
                mQueue.add(new NormalPostRequest(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        isShow = true;
                        Log.e("progressDialog", "dismiss");
                        jsonListener.jsonListener(jsonObject);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new Toastor(MyApplication.newInstance().getApplicationContext()).showSingletonToast("服务器连接失败");
                        Log.e("---", error.toString());
                        isShow = true;
                        progressDialog.dismiss();
                        Log.e("progressDialog", "dismiss");

                    }
                }, map));
            }
        }).start();


    }

    public NetworkRequests initViw(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中...");
        progressDialog.setTitle("");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);
        return instance;
    }




    public void makeHTTPrequest(String url, final JsonDataReturnListener jsonListener) {
        if (!progressDialog.isShowing() && isShow) {
            progressDialog.show();
            isShow = true;
        }
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("url", response.toString());
                        progressDialog.dismiss();
                        isShow = true;
                        jsonListener.jsonListener(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("url", error.toString());
                progressDialog.dismiss();
                isShow = true;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "APPCODE da813ac5cb7e40ffaf48af8716ee455b");
                return headers;
            }
        };
        RequestQueue mQueue = MyApplication.newInstance().getmQueue();
        mQueue.add(jsonObjRequest);
    }

    public void Requests(final String url, final Map<String, String> map, final JsonDataReturnListener jsonListener) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue mQueue = MyApplication.newInstance().getmQueue();
                mQueue.add(new NormalPostRequest(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {


                        jsonListener.jsonListener(jsonObject);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("progressDialog", "dismiss");

                    }
                }, map));
            }
        }).start();


    }
}
