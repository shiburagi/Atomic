package com.infideap.atomic;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class Future<T> implements java.util.concurrent.Future<T>{
    private Thread thread;
    private FutureCallback<?> callback;

    public Future<T> setCallback(final FutureCallback<?> callback) {
        this.callback = callback;

        return this;
    }


    @Override
    public boolean cancel(boolean b) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public T get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
