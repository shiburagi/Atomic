package com.infideap.atomic

/**
 * Created by Shiburagi on 20/10/2016.
 */
interface FutureCallback<T> {
    fun onCompleted(e: Exception?, result: T)
}