package com.payture.pilxwallet.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.payture.pilxwallet.R;
import com.payture.pilxwallet.api.ApiWorker;
import com.payture.pilxwallet.chart.ChartFragment;
import com.payture.pilxwallet.data.DTO.ExchangeDTO;
import com.payture.pilxwallet.exchange.ExchangeFragment;
import com.payture.pilxwallet.rate.RateFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IView {

    private ApplicationPresenter presenter;


    private Fragment balanceFragment;
    private Fragment mainFragment;
    private Fragment rateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new ApplicationPresenter(this);
        ApiWorker.getInstance(getApplicationContext()).initialize();

        presenter.openDefaultFragments();

        presenter.setWalletDirectory(getFilesDir().getAbsoluteFile());
        presenter.initBitcoin();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        //} else if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
        //    getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exchange) {
            this.openExchangeFragment();
        }

        if (id == R.id.nav_chart) {
            this.openChartFragment();
        }

        if (id == R.id.nav_history) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openBalanceFragment(){
        //balanceFragment = new BalanceFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.balance_frame, balanceFragment).commit();
    }

    @Override
    public void openRateFragment(){
        rateFragment = new RateFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.rates_frame, rateFragment).commit();
    }

    @Override
    public void startExchange(ExchangeDTO exchangeParams) {
        presenter.onStartExchange(exchangeParams);
    }

    @Override
    public void openCardFragment() {}

    @Override
    public void openExchangeFragment() {
        mainFragment = new ExchangeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, mainFragment)
                .addToBackStack(ExchangeFragment.class.getName())
                .commit();
    }

    @Override
    public void openChartFragment(){
        mainFragment = new ChartFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, mainFragment)
                .addToBackStack(ChartFragment.class.getName())
                .commit();    }

    @Override
    public void updateBitcoinSyncProgress(int percentage){
        ProgressBar syncProgressBar = (ProgressBar)findViewById(R.id.sync_progress_bar);
        if (syncProgressBar != null) {
            syncProgressBar.setProgress(percentage);
        }
    }

    @Override
    public void updateBitcoinWalletInfo(String receiveAddress, String balance){
        TextView addressTextView = (TextView)findViewById(R.id.user_address);
        if (addressTextView != null)
            addressTextView.setText(receiveAddress);

        TextView balanceTextView = (TextView)findViewById(R.id.user_balance_amount);
        if (balanceTextView != null)
            balanceTextView.setText(balance);
    }
}
