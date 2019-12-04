package com.infideap.atomexample

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.infideap.atomic.Atom.Companion.setClient
import okhttp3.OkHttpClient

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor()).build()
        setClient(client)
    }
}