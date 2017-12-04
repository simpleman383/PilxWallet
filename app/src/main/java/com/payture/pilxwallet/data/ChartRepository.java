package com.payture.pilxwallet.data;

import com.google.gson.Gson;
import com.payture.pilxwallet.api.ApiWorker;
import com.payture.pilxwallet.api.apiresponse.ChartAPIResponse;
import com.payture.pilxwallet.data.DTO.ChartDTO;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by simpleman383 on 30.11.17.
 */

public class ChartRepository implements IRepository<ChartDTO[]> {

    public ChartDTO[] getData() {
        ChartAPIResponse apiResponse = ApiWorker.getChartApi().getPriceHistory();

        if (apiResponse.isSuccess())
        {
            return parseApiResponse(apiResponse.getResponseBody());
        }
        return null;
    }

    public boolean saveData(ChartDTO[] dtos) {
        return false;
    }

   private ChartDTO[] parseApiResponse(JSONObject BTCPriceHistoryJSON){
        if (BTCPriceHistoryJSON != null) {
            Gson gson = new Gson();
            JSONArray jsonArray = BTCPriceHistoryJSON.optJSONArray("values");

            if (jsonArray != null) {
                ChartDTO[] dtos = gson.fromJson(jsonArray.toString(), ChartDTO[].class);
                return dtos;
            }
        }
        return null;
    }
}
