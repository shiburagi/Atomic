package com.infideap.atomic

import okhttp3.Response

/**
 * Created by Shiburagi on 20/10/2016.
 */
class ResponseFutureCallback<T> : FutureCallback<T> {
    override fun onCompleted(e: Exception?, result: T) {}
    fun onCompleted(e: Exception?, result: T, response: Response?) {
        onCompleted(e, result)
    }
}