package com.payture.pilxwallet.api.apiresponse;

import org.json.JSONObject;

/**
 * Created by simpleman383 on 30.11.17.
 */

public class ChartAPIResponse {
    private boolean success;
    private JSONObject responseBody;
    private String error;

    public ChartAPIResponse(boolean success, JSONObject body, String error) {
        this.success = success;
        this.responseBody = body;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public JSONObject getResponseBody() {
        return responseBody;
    }

    public String getError() {
        return error;
    }

}
