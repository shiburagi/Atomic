package com.infideap.atomic

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*

/**
 * Created by Shiburagi on 20/10/2016.
 */
class Atom {
    fun addInterceptor(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }

    fun removeInterceptor(interceptor: Interceptor?) {
        interceptors.remove(interceptor)
    }

    companion object {
        const val PUT_METHOD = "PUT"
        const val PATCH_METHOD = "PATCH"
        const val GET_METHOD = "GET"
        const val POST_METHOD = "POST"
        const val DELETE_METHOD = "DELETE"
        val LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC
        var LOG_BODY = false
        private val interceptors: MutableList<Interceptor> = ArrayList()
        @JvmStatic
        fun with(context: Context): A {
            return A(context, interceptors)
        }

        fun client(): OkHttpClient? {
            return A.Companion.globalClient
        }

        @JvmStatic
        fun setClient(client: OkHttpClient?) {
            A.Companion.globalClient = client
        }
    }
}