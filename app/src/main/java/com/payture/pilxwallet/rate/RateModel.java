package com.payture.pilxwallet.rate;

import com.payture.pilxwallet.data.DTO.RateDTO;
import com.payture.pilxwallet.data.RateRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class RateModel {

    private static RateModel instance;
    public static RateModel getInstance() {
        if ( instance == null )
            instance = new RateModel();
        return instance;
    }

    public enum Currency {
        USD, EUR;

        public static boolean contains(String value) {
            for (Currency c : Currency.values()) {
                if ( c.name().equals(value) )
                    return true;
            }
            return false;
        }
    }

    HashMap<Currency, Double> rates;
    RateRepository rateRepository;

    private RateModel()
    {
        rates = new HashMap<>();
        rateRepository = new RateRepository();

    }

    public void setRate(String currency, Double rate)
    {
        if (Currency.contains(currency))
            rates.put(Currency.valueOf(currency), rate);
    }

    public double getRate(String currency)
    {
        if (Currency.contains(currency) && rates.containsKey(Currency.valueOf(currency)) )
            return rates.get(Currency.valueOf(currency));
        return 0;
    }

    public String[] getCurrencies() {
        if (rates.size() == 0)
            updateModel();
        ArrayList<String> currencies = new ArrayList<>();
        for (Currency currency : rates.keySet()) {
            currencies.add(currency.name());
        }
        return currencies.toArray(new String[currencies.size()]);
    }

    public void updateModel() {
        RateDTO[] dtos = rateRepository.getData();
        if ( dtos != null )
        {
            for (RateDTO dto : dtos) {
                Double rateValue = Double.valueOf(dto.getRate());
                setRate(dto.getCurrency(), rateValue);
            }
        }
    }

}
