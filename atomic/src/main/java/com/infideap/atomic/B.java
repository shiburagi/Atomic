package com.infideap.atomic;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class B<T> extends AtomRequest<File> {
    private static final String TAG = B.class.getSimpleName();

    public B(A a, File file) {
        super(a, file.getClass());
        this.t = file;
    }

    @Override
    public File parse(Response response) throws IOException {
        BufferedSink sink = Okio.buffer(Okio.sink(t));
        sink.writeAll(response.body().source());
        sink.close();
        try {
            response.body().close();
        } catch (Exception ignored) {
        }
        return t;
    }


    @Override
    public void setCallback(FutureCallback<File> callback) {
        super.setCallback(callback);
    }

    @Override
    public File get() throws IOException {
        return super.get();
    }
}
