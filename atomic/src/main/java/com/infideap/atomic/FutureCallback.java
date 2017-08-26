package com.infideap.atomic;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public interface FutureCallback<T> {
    public void onCompleted(Exception e, T result);
}
