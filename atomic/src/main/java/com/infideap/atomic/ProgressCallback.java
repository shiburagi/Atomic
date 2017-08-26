package com.infideap.atomic;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public interface ProgressCallback {
    public void onProgress(long downloaded, long total);
}
