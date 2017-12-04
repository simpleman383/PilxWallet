package com.payture.pilxwallet.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by simpleman383 on 30.11.17.
 */

public  class AppRequestQueue {
    private static AppRequestQueue queueInstance;
    private static RequestQueue mRequestQueue;
    private static Context context;

    private final static String BASE_URL = "http://10.0.2.2:53039";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private AppRequestQueue(Context ctx){
        context = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppRequestQueue getInstance(Context context){
        if (queueInstance == null) {
            queueInstance = new AppRequestQueue(context);
        }
        return queueInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
