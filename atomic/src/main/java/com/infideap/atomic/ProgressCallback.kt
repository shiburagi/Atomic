package com.infideap.atomic

/**
 * Created by Shiburagi on 20/10/2016.
 */
interface ProgressCallback {
    fun onProgress(downloaded: Long, total: Long)
}