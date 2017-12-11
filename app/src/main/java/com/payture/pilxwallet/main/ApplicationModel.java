package com.payture.pilxwallet.main;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletChangeEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by simpl on 12/4/2017.
 */

public class ApplicationModel {
    private static ApplicationModel instance = new ApplicationModel();

    public static ApplicationModel getInstance() {
        if (instance == null)
            instance = new ApplicationModel();
        return instance;
    }

    private ArrayList<IWalletEventListener> listeners = new ArrayList<>();
    public void addWalletEventListener(IWalletEventListener listener){
        listeners.add(listener);
    }

    private void broadcastSyncProgressEvent(double progress) {
        for (IWalletEventListener listener : listeners)
            listener.onSyncProgress(progress);
    }

    private void broadcastWalletSetupEvent() {
        for (IWalletEventListener listener : listeners)
            listener.onWalletSetup();
    }




    private ExchangeDTO exchangeParams;
    public void setExchangeParams(ExchangeDTO params) {
        exchangeParams = params;
    }


    private static NetworkParameters networkParameters = TestNet3Params.get();
    private static WalletAppKit walletAppKit;

    private File bitcoinWalletDir;

    private static final String WALLET_FILE_NAME = "pilxwallet";

    public void setWalletDirectory(File directory){
        bitcoinWalletDir = directory;
    }

    public Wallet getBitcoinWallet(){
        return walletAppKit.wallet();
    }

    public void initBitcoinWallet() {

        File bitcoinWalletFile = new File(bitcoinWalletDir.getPath());

        walletAppKit = new WalletAppKit(networkParameters, bitcoinWalletFile, WALLET_FILE_NAME){
            @Override
            protected void onSetupCompleted() {
                wallet().allowSpendingUnconfirmedTransactions();

                Threading.USER_THREAD.execute(new Runnable() {
                    @Override
                    public void run() {
                        broadcastWalletSetupEvent();
                    }
                });
            }
        };

        walletAppKit.setDownloadListener(new DownloadProgressTracker(){
            @Override
            protected void progress(double pct, int blocksSoFar, Date date) {
                super.progress(pct, blocksSoFar, date);
                broadcastSyncProgressEvent(pct);
            }

            @Override
            public void onChainDownloadStarted(Peer peer, int blocksLeft) {
                super.onChainDownloadStarted(peer, blocksLeft);
            }

            @Override
            protected void doneDownload() {
                super.doneDownload();
            }
        });

        if (networkParameters == RegTestParams.get()) {
            walletAppKit.connectToLocalHost();   // You should run a regtest mode bitcoind locally.
        }

        walletAppKit.setBlockingStartup(false).setUserAgent("PilxWallet", "1.0");

        walletAppKit.startAsync();

        //if (seed != null)
        //   walletAppKit.restoreWalletFromSeed(seed);

    }

}
