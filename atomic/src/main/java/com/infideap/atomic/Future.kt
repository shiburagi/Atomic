package com.infideap.atomic

import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by Shiburagi on 20/10/2016.
 */
class Future<T> : java.util.concurrent.Future<T?> {
    private val thread: Thread? = null
    private var callback: FutureCallback<*>? = null
    fun setCallback(callback: FutureCallback<*>?): Future<T> {
        this.callback = callback
        return this
    }

    override fun cancel(b: Boolean): Boolean {
        return false
    }

    override fun isCancelled(): Boolean {
        return false
    }

    override fun isDone(): Boolean {
        return false
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    override fun get(): T? {
        return null
    }

    @Throws(InterruptedException::class, ExecutionException::class, TimeoutException::class)
    override fun get(l: Long, timeUnit: TimeUnit): T? {
        return null
    }
}