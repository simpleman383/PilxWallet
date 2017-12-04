package com.payture.pilxwallet.data.DTO;

/**
 * Created by simpleman383 on 30.11.17.
 */

public class ChartDTO {

    private long x;
    private float y;

    public ChartDTO(long x, float y) {
        this.x = x;
        this.y = y;
    }

    public long getDate() {
        return x;
    }

    public float getPrice() {
        return y;
    }

}
