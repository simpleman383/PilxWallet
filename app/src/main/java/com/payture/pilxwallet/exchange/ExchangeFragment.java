package com.payture.pilxwallet.exchange;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.payture.pilxwallet.IView;
import com.payture.pilxwallet.MainActivity;
import com.payture.pilxwallet.R;
import com.payture.pilxwallet.data.DTO.ExchangeDTO;
import com.payture.pilxwallet.rate.RatePresenter;
import com.payture.pilxwallet.utils.Formatter;

/**
 * Created by simpleman383 on 02.12.17.
 */

public class ExchangeFragment extends Fragment implements IExchangeView {

    private IView mainActivity;
    private IExchangePresenter presenter;

    private TabHost tabHost;
    private EditText purchaseAmountEditText;
    private EditText purchaseCostEditText;
    private Spinner purchaseSpinner;
    private Button purchaseButton;

    private EditText saleAmountEditText;
    private EditText saleCostEditText;
    private Spinner saleSpinner;
    private Button saleButton;

    private TextView purchaseBTCLabel;
    private TextView saleBTCLabel;

    private Tab prevTab;

    enum Tab { PURCHASE, SALE;
        public static Tab getTabById(int id) {
            if (id < 0 || id > Tab.values().length - 1)
                return null;
            return Tab.values()[id];
        }
    }

    enum FieldType { AMOUNT, COST }

    class ExchangeTextWatcher implements TextWatcher {
        EditText editText;
        FieldType type;
        Tab tab;
        boolean isValid = false;

        public ExchangeTextWatcher(EditText field, Tab tab, FieldType type){
            this.editText = field;
            this.type = type;
            this.tab = tab;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            isValid = true;
            makeControlsValid(tab);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (editText.hasFocus())
                switch (type){
                    case AMOUNT: {
                        isValid = presenter.onAmountChanged(charSequence.toString());
                        break;
                    }
                    case COST: {
                        isValid = presenter.onCostChanged(charSequence.toString());
                        break;
                    }
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!isValid)
                makeControlsInvalid(tab);
        }
    }

    class CurrencySpinnerAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private String[] currencies;

        CurrencySpinnerAdapter(Context context, String[] currencies) {
            inflater = LayoutInflater.from(context);
            this.currencies = currencies;
        }

        @Override
        public String getItem(int i) {
            return currencies[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            View convertView = inflater.inflate(R.layout.currency_spinner_item, null);
            TextView textView = (TextView)convertView.findViewById(R.id.currency_spinner_item_text);
            textView.setText(currencies[index]);

            if (index != 0) {
                ImageView caret = (ImageView)convertView.findViewById(R.id.currency_spinner_item_caret);
                caret.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return currencies.length;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        presenter = new ExchangePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exchange_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewComponents();
        showControls(prevTab);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onFragmentStop();
    }

    private void initViewComponents(){
        initTabHost();
        initTabViewComponents(Tab.PURCHASE);
        initTabViewComponents(Tab.SALE);
    }

    private void initTabHost() {
        tabHost = (TabHost)getView().findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(Tab.PURCHASE.name());
        tabSpec.setContent(R.id.tab_purchase_exchange_layout);
        tabSpec.setIndicator(getResources().getString(R.string.exchange_purchase_tab_header));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(Tab.SALE.name());
        tabSpec.setContent(R.id.tab_sale_exchange_layout);
        tabSpec.setIndicator(getResources().getString(R.string.exchange_sale_tab_header));
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Tab newTab = Tab.valueOf(tabId);
                presenter.onSelectedTabChanged(newTab);
                showControls(newTab);
                prevTab = newTab;
                Log.i("BTCPurchaseSaleFragment", "onTabChanged - newTab - " + tabId);
            }
        });

        tabHost.setCurrentTab(0);
        prevTab = Tab.getTabById(0);
        return;
    }

    private void initTabViewComponents(Tab tab) {
        initCurrencySpinner(tab);
        initEditTextFields(tab);
        initButton(tab);
        return;
    }

    private void initEditTextFields(final Tab tab) {
        switch (tab) {
            case PURCHASE:
            {
                purchaseBTCLabel = (TextView) getView().findViewById(R.id.tab_purchase_exchange_transaction_amount_currency_label);
                purchaseAmountEditText = (EditText) getView().findViewById(R.id.tab_purchase_exchange_transaction_amount);
                purchaseCostEditText = (EditText) getView().findViewById(R.id.tab_purchase_exchange_transaction_cost);
                purchaseAmountEditText.addTextChangedListener(new ExchangeTextWatcher(purchaseAmountEditText, tab, FieldType.AMOUNT));
                purchaseCostEditText.addTextChangedListener(new ExchangeTextWatcher(purchaseCostEditText, tab, FieldType.COST));
                break;
            }
            case SALE: {
                saleBTCLabel = (TextView) getView().findViewById(R.id.tab_sale_exchange_transaction_amount_currency_label);
                saleAmountEditText = (EditText) getView().findViewById(R.id.tab_sale_exchange_transaction_amount);
                saleCostEditText = (EditText) getView().findViewById(R.id.tab_sale_exchange_transaction_cost);
                saleAmountEditText.addTextChangedListener(new ExchangeTextWatcher(saleAmountEditText, tab, FieldType.AMOUNT));
                saleCostEditText.addTextChangedListener(new ExchangeTextWatcher(saleCostEditText, tab, FieldType.COST));
                break;
            }
        }
    }

    private void makeControlsInvalid(Tab tab){
        switch (tab) {
            case PURCHASE: {
                if ( purchaseAmountEditText != null )
                    purchaseAmountEditText.setTextColor(getResources().getColor(R.color.colorTextInvalid));
                if ( purchaseCostEditText != null )
                    purchaseCostEditText.setTextColor(getResources().getColor(R.color.colorTextInvalid));
            }
            case SALE: {
                if ( saleAmountEditText != null )
                    saleAmountEditText.setTextColor(getResources().getColor(R.color.colorTextInvalid));
                if ( saleCostEditText != null )
                    saleCostEditText.setTextColor(getResources().getColor(R.color.colorTextInvalid));
            }
        }
    }

    private void makeControlsValid(Tab tab){
        switch (tab) {
            case PURCHASE: {
                if ( purchaseAmountEditText != null )
                    purchaseAmountEditText.setTextColor(Color.BLACK);
                if ( purchaseCostEditText != null )
                    purchaseCostEditText.setTextColor(Color.BLACK);
                break;
            }
            case SALE: {
                if ( saleAmountEditText != null )
                    saleAmountEditText.setTextColor(Color.BLACK);
                if ( saleCostEditText != null )
                    saleCostEditText.setTextColor(Color.BLACK);
                break;
            }

        }
    }

    public void updateAmount(double amount){
        final Tab currentTab = Tab.getTabById(tabHost.getCurrentTab());
        switch (currentTab){
            case PURCHASE:{
                if ( purchaseAmountEditText != null ) {
                    String amountText = amount == 0 ? "" : Formatter.toBTCFormat(amount);
                    purchaseAmountEditText.setText(amountText);
                }
                break;
            }
            case SALE: {
                if ( saleAmountEditText != null ) {
                    String amountText = amount == 0 ? "" : Formatter.toBTCFormat(amount);
                    saleAmountEditText.setText(amountText);
                }
                break;
            }
        }
    }

    public void updateCost(double cost){
        final Tab currentTab = Tab.getTabById(tabHost.getCurrentTab());
        switch (currentTab) {
            case PURCHASE: {
                if ( purchaseCostEditText != null ) {
                    String costText = cost == 0 ? "" : Formatter.toCurrencyFormat(cost);
                    purchaseCostEditText.setText(costText);
                }
                break;
            }
            case SALE: {
                if ( saleCostEditText != null ) {
                    String costText = cost == 0 ? "" : Formatter.toCurrencyFormat(cost);
                    saleCostEditText.setText(costText);
                }
                break;
            }
        }
    }

    public void updateSpinnerData(String[] currencies) {
        if (getContext() != null) {
            CurrencySpinnerAdapter adapter = new CurrencySpinnerAdapter(getContext(), currencies);
            // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.currency_spinner_item, currencies);

            if (purchaseSpinner != null)
                purchaseSpinner.setAdapter(adapter);

            if (saleSpinner != null)
                saleSpinner.setAdapter(adapter);
        }
    }

    private void initCurrencySpinner(Tab tab) {
        presenter.getCurrenciesAvailable();
        switch (tab) {
            case PURCHASE: {
                purchaseSpinner = (Spinner)getView().findViewById(R.id.btc_purchase_spinner);
                purchaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if ( view != null )
                        {
                            TextView currencyTextView = (TextView) view.findViewById(R.id.currency_spinner_item_text);
                            presenter.onCurrencyChanged(currencyTextView.getText().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            case SALE: {
                saleSpinner = (Spinner)getView().findViewById(R.id.tab_sale_exchange_spinner);
                saleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView currencyTextView = (TextView)view.findViewById(R.id.currency_spinner_item_text);
                        if (currencyTextView != null)
                            presenter.onCurrencyChanged(currencyTextView.getText().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        }


    }

    public void clearPreviousTab() {
        switch (prevTab) {
            case PURCHASE: {
                purchaseAmountEditText.setText("");
                purchaseCostEditText.setText("");
                purchaseSpinner.setSelection(0);
                break;
            }
            case SALE: {
                saleAmountEditText.setText("");
                saleCostEditText.setText("");
                saleSpinner.setSelection(0);
                break;
            }
        }
    }

    private void initButton(Tab tab) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionClick();
            }
        };

        switch (tab) {
            case PURCHASE: {
                purchaseButton = (Button)getView().findViewById(R.id.tab_purchase_exchange_action_button);
                purchaseButton.setOnClickListener(clickListener);
                break;
            }

            case SALE: {
                saleButton = (Button)getView().findViewById(R.id.tab_sale_exchange_action_button);
                saleButton.setOnClickListener(clickListener);
                break;
            }
        }
    }

    private void showControls(Tab tab){
        Animation slideLeft = AnimationUtils.loadAnimation(getContext(), R.anim.show_left);
        Animation slideRight = AnimationUtils.loadAnimation(getContext(), R.anim.show_right);

        switch (tab){
            case PURCHASE: {
                if (purchaseAmountEditText != null) {
                    purchaseAmountEditText.startAnimation(slideLeft);
                    purchaseAmountEditText.setVisibility(View.VISIBLE);
                }
                if (purchaseCostEditText != null) {
                    purchaseCostEditText.startAnimation(slideLeft);
                    purchaseCostEditText.setVisibility(View.VISIBLE);
                }
                if (purchaseBTCLabel != null) {
                    purchaseBTCLabel.startAnimation(slideRight);
                    purchaseBTCLabel.setVisibility(View.VISIBLE);
                }
                if (purchaseSpinner != null) {
                    purchaseSpinner.startAnimation(slideRight);
                    purchaseSpinner.setVisibility(View.VISIBLE);
                }
                if (purchaseButton != null) {
                    purchaseButton.startAnimation(slideLeft);
                    purchaseButton.setVisibility(View.VISIBLE);
                }
                break;
            }

            case SALE: {
                if (saleAmountEditText != null) {
                    saleAmountEditText.startAnimation(slideLeft);
                    saleAmountEditText.setVisibility(View.VISIBLE);
                }
                if (saleCostEditText != null) {
                    saleCostEditText.startAnimation(slideLeft);
                    saleCostEditText.setVisibility(View.VISIBLE);
                }
                if (saleBTCLabel != null) {
                    saleBTCLabel.startAnimation(slideRight);
                    saleBTCLabel.setVisibility(View.VISIBLE);
                }
                if (saleSpinner != null)
                {
                    saleSpinner.startAnimation(slideRight);
                    saleSpinner.setVisibility(View.VISIBLE);
                }
                if (saleButton != null)
                {
                    saleButton.startAnimation(slideLeft);
                    saleButton.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }

    public void startExchange(ExchangeDTO exchangeParams){
        if (mainActivity != null)
            mainActivity.startExchange(exchangeParams);
    }
}
