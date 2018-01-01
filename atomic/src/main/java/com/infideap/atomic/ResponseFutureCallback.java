package com.infideap.atomic;

import okhttp3.Response;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class ResponseFutureCallback<T> implements FutureCallback<T> {
    public void onCompleted(Exception e, T result) {

    }

    public void onCompleted(Exception e, T result, Response response) {
        onCompleted(e, result);
    }

    ;
}