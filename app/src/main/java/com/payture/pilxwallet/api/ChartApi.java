package com.payture.pilxwallet.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.payture.pilxwallet.api.apiresponse.ChartAPIResponse;
import com.payture.pilxwallet.data.DTO.ChartDTO;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by simpleman383 on 30.11.17.
 */

public class ChartApi {

    ChartApi(Context context) {
        this.context = context;
    }

    private Context context;
    private final static String URL = "https://api.blockchain.info/charts/market-price";
    private final static int METHOD = Request.Method.GET;

    public ChartAPIResponse getPriceHistory() {

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(METHOD, URL, null, future, future){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("format", "json");
                return params;
            }
        };

        AppRequestQueue.getInstance(context).addToRequestQueue(request);

        try {
            JSONObject response = future.get();
            return new ChartAPIResponse(true, response, null);
        } catch (Exception ex) {
            Log.e("ChartApi", "getPriceHistory(): " + ex.toString());
            return new ChartAPIResponse(false, null, ex.toString());
        }
    }

}
