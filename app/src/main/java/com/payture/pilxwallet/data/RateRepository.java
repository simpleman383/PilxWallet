package com.payture.pilxwallet.data;

import com.payture.pilxwallet.data.DTO.RateDTO;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class RateRepository implements IRepository<RateDTO[]> {

    @Override
    public RateDTO[] getData() {

        try {
            Thread.sleep(2000);
        } catch (Exception ex) {}

        RateDTO[] rates = new RateDTO[]{
            new RateDTO("USD", System.currentTimeMillis() % 1000),
                new RateDTO("EUR", System.currentTimeMillis() % 100),
        };
        return rates;
    }

    @Override
    public boolean saveData(RateDTO[] data) {
        return false;
    }

}
