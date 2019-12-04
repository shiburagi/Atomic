package com.infideap.atomic

import android.os.Handler
import android.os.Looper
import com.infideap.atomic.AtomRequest
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.lang.reflect.Type

/**
 * Created by Shiburagi on 20/10/2016.
 */
open class AtomRequest<T> internal constructor(private val a: A, aClass: Class<T>?) {
    private val aClass: Class<*>? = aClass
    protected var t: T? = null

    init {
        a.createClient()
    }

//    internal constructor(a: A, t: Type) {
//        this.a = a
//        aClass = t.javaClass
//        a.createClient()
//    }

    fun notNull(t:T?):T{
        return t?:aClass?.newInstance() as T;
    }

    open fun setCallback(callback: FutureCallback<T>) {
        prepare().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val handler = Handler(Looper.getMainLooper())
                handler.post { onCompleted(e, t, null) }
            }

            override fun onResponse(call: Call, response: Response) {
                val exception: Exception?
                exception = try {
                    parse(response)
                    null
                } catch (e: Exception) {
                    e
                }
                val handler = Handler(Looper.getMainLooper())
                handler.post { onCompleted(exception, t, response) }
            }

            private fun onCompleted(e: Exception?, t: T?, response: Response?) {
                if (callback is ResponseFutureCallback<T>) {
                    callback.onCompleted(e, notNull(t), response)
                } else callback.onCompleted(e, notNull(t))
            }
        })
    }

    @Throws(IOException::class)
    open fun get(): T? {
        return parse(prepare().execute())
    }

    @Throws(IOException::class)
    protected open fun parse(response: Response): T? {
        t = if (a.isString) {
            response.body!!.string() as T
        } else {
            a.gson.fromJson(response.body!!.charStream(), aClass) as T
        }
        try {
            response.body!!.close()
        } catch (ignored: Exception) {
        }
        return t
    }

    protected fun prepare(): Call {
        var requestBody = RequestBody.create(A.Companion.JSON, if (a.requestBody == null) "" else a.requestBody.toString())
        if (a.parts != null) {
            if (a.parts!!.size > 0) {
                var builder = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                for (part in a.parts!!) {
                    val mediaType: MediaType = "application/octet-stream".toMediaTypeOrNull()!!
                    builder = if (part is FilePart) {
                        val filePart = part
                        if (filePart.file == null) continue
                        val body = RequestBody.create(mediaType, filePart.file!!)
                        builder.addFormDataPart(part.key, filePart.name, body)
                    } else {
                        builder.addFormDataPart(part.key, part.name?:"")
                    }
                }
                if (a.requestBody != null) builder.addPart(requestBody)
                requestBody = builder.build()
            }
        }
        val builder = Request.Builder()
                .url(a.url?:"")
        if (a.headers != null) builder.headers(a.headers!!)
        val request = if (a.method == null) if (a.requestBody == null) builder.get().build() else builder.post(requestBody).build() else builder.method(a.method!!.toUpperCase(), requestBody).build()
        a.request = request
        return a.client!!.newCall(request)
    }

    @Throws(IOException::class)
    fun execute(): Response {
        return prepare().execute()
    }

    companion object {
        private val TAG = AtomRequest::class.java.simpleName
    }
}