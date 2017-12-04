package com.payture.pilxwallet.data;

/**
 * Created by simpleman383 on 21.11.17.
 */

public interface IRepository<T> {
    T getData();
    boolean saveData(T data);
}
