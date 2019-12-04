package com.infideap.atomic

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Shiburagi on 20/10/2016.
 */
class A(var context: Context, private val interceptors: MutableList<Interceptor>) {
    private var connectTimeout = 1L
    private var readTimeout = 3L
    private var connectTimeUnit = TimeUnit.MINUTES
    private var readTimeUnit = TimeUnit.MINUTES
    var client: OkHttpClient? = null

    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaTypeOrNull()!!
        private val TAG = A::class.java.simpleName
        var globalClient: OkHttpClient? = null

        init {
            globalClient = OkHttpClient()
        }
    }

    var url: String? = null
        private set
    var downlaodProgressCallback: ProgressCallback? = null
    var requestBody: StringBuilder? = null
        private set
    var gson: Gson
    var uploadProgressCallback: ProgressCallback? = null
    internal var parts: MutableList<Part> = ArrayList()
    var isString = false
    var headers: Headers? = null
    var method: String? = null
    var request: Request? = null
    fun load(url: String?): A {
        this.url = url
        return this
    }

    fun load(url: String?, method: String?): A {
        this.url = url
        this.method = method
        return this
    }

    fun progress(progressCallback: ProgressCallback?): A {
        downlaodProgressCallback = progressCallback
        return this
    }

    fun write(file: File): B<*> {
        return B<Any?>(this, file)
    }

    fun setJsonPojoBody(request: Any?): A {
        requestBody = StringBuilder()
        gson.toJson(request, requestBody)
        return this
    }

    fun setBody(request: String?): A {
        requestBody = StringBuilder(request)
        return this
    }

    fun asString(): C<String> {
        isString = true
        return C<String>(this, String::class.java)
    }

    fun uploadProgress(progressCallback: ProgressCallback?): A {
        uploadProgressCallback = progressCallback
        return this
    }

    fun addMultipartParts(parts: MutableList<Part>): A {
        this.parts = parts
        method = Atom.Companion.POST_METHOD
        return this
    }

    fun <T> `as`(t: Class<T>?): C<T> {
        val c = C<T>(this, t)
        isString = false
        return c
    }

    @Throws(IOException::class)
    fun <T> execute(t: Class<T>?): Response? {
        val z = AtomRequest(this, t)
        isString = false
        return z.execute()
    }

    fun getParts(): List<Part>? {
        return parts
    }

    fun setMultipartFile(key: String?, file: File): A {
        if (method == null) {
            method = Atom.Companion.POST_METHOD
        }
        parts.add(FilePart(key, file))
        return this
    }

    fun setMultipart(key: String?, name: String?): A {
        if (method == null) {
            method = Atom.Companion.POST_METHOD
        }
        parts.add(Part(key?:"", name))
        return this
    }

    fun setHeader(headers: Headers?): A {
        this.headers = headers
        return this
    }

    fun setConnectTimeout(timeout: Long, unit: TimeUnit): A {
        connectTimeout = timeout
        connectTimeUnit = unit
        return this
    }

    fun setReadTimeout(timeout: Long, unit: TimeUnit): A {
        readTimeout = timeout
        readTimeUnit = unit
        return this
    }

    fun createClient() {
        val builder = globalClient!!.newBuilder()
                .connectTimeout(connectTimeout, connectTimeUnit)
                .readTimeout(readTimeout, readTimeUnit)
                .addNetworkInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val originalResponse = chain.proceed(chain.request())
                        val progress = ProgressResponseBody(originalResponse.body,
                                downlaodProgressCallback)
                        return originalResponse.newBuilder()
                                .body(progress)
                                .build()
                    }
                })
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        if (Atom.Companion.LOG_BODY) {
            val logging = HttpLoggingInterceptor()
            logging.level = Atom.Companion.LOG_LEVEL
            builder.addInterceptor(logging)
        }
        client = builder.build()
    }

    fun addInterceptor(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }

    fun removeInterceptor(interceptor: Interceptor?) {
        interceptors.remove(interceptor)
    }

    init {
        val builder = GsonBuilder()
        gson = builder.create()
    }
}