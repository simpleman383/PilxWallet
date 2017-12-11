package com.payture.pilxwallet.main;

/**
 * Created by simpl on 12/7/2017.
 */

public class User {

    private String phone;

    public User(String phone) {
        this.phone = phone;
    }

    public String getUsername(){
        return phone;
    }

}
