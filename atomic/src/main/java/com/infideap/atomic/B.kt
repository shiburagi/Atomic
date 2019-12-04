package com.infideap.atomic

import okhttp3.Response
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException

/**
 * Created by Shiburagi on 20/10/2016.
 */
class B<T>(a: A, file: File) : AtomRequest<File>(a, file.javaClass) {
    @Throws(IOException::class)
    public override fun parse(response: Response): File? {
        if (t != null) {
            val sink: BufferedSink = t!!.sink().buffer()
            sink.writeAll(response.body!!.source())
            sink.close()
            try {
                response.body!!.close()
            } catch (ignored: Exception) {
            }
        }
        return t
    }

    @Throws(IOException::class)
    override fun get(): File? {
        return super.get()
    }

    companion object {
        private val TAG = B::class.java.simpleName
    }

    init {
        t = file
    }
}