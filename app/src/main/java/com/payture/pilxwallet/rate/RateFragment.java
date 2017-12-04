package com.payture.pilxwallet.rate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.payture.pilxwallet.R;
import com.payture.pilxwallet.utils.Formatter;


public class RateFragment extends Fragment implements IRateView {

    IRatePresenter presenter;

    TextView rateUSD;
    TextView rateEUR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RatePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewCreated();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_layout, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onFragmentStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rateUSD = (TextView) view.findViewById(R.id.currency_BTC_USD_price);
        rateEUR = (TextView) view.findViewById(R.id.currency_BTC_EUR_price);
    }

    @Override
    public void updateRates() {

        if (rateUSD != null) {
            double rate = presenter.getRate("USD");
            rateUSD.setText(Formatter.toCurrencyFormat(rate));
        }

        if (rateEUR != null) {
            double rate = presenter.getRate("EUR");
            rateEUR.setText(Formatter.toCurrencyFormat(rate));
        }

    }
}
