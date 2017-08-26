package com.infideap.atomic;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class B<T> {
    private static final String TAG = B.class.getSimpleName();
    private final A a;
    private final File file;
    private FutureCallback<File> callback;
    private Thread thread;

    public B(A a, File file) {
        this.a = a;
        this.file = file;
        a.createClient();
    }

    public void setCallback(final FutureCallback<File> callback) {
        this.callback = callback;

        thread = new Thread() {
            public void run() {
                Exception exception = null;
                File file = null;
                try {
                    file = get();
                } catch (IOException e) {
                    exception = e;
                } finally {
                    callback.onCompleted(exception, file);
                }
            }
        };

        thread.start();
    }

    public File get() throws IOException {
        RequestBody requestBody = RequestBody.create(A.JSON, a.getRequestBody() == null ? "" : a.getRequestBody().toString());
        Request.Builder builder = new Request.Builder()
                .url(a.getUrl());

        if (a.headers != null)
            builder.headers(a.headers);

        Request request = a.getRequestBody() == null ?
                builder.get().build() :
                builder.post(requestBody).build();


        a.request = request;
        Response response = a.client.newCall(request).execute();
        BufferedSink sink = Okio.buffer(Okio.sink(file));
        sink.writeAll(response.body().source());
        sink.close();
        try {
            response.body().close();
        } catch (Exception ignored) {
        }
        return file;
    }
}
