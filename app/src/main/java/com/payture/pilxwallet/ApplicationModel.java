package com.payture.pilxwallet;

/**
 * Created by simpl on 12/4/2017.
 */

public class ApplicationModel {
    private static ApplicationModel instance;
    private ApplicationModel(){}

    public static ApplicationModel getInstance() {
        if (instance == null)
            instance = new ApplicationModel();
        return instance;
    }


}
