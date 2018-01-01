package com.infideap.atomic;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class AtomRequest<T> {
    private static final String TAG = AtomRequest.class.getSimpleName();
    private final A a;
    private final Class<?> aClass;

    protected T t = null;

    AtomRequest(A a, Class<T> aClass) {
        this.a = a;
        this.aClass = aClass;

        a.createClient();
    }

    AtomRequest(A a, Type t) {
        this.a = a;
        this.aClass = t.getClass();

        a.createClient();
    }


    public void setCallback(final FutureCallback<T> callback) {


        prepare().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onCompleted(e, t, null);
                    }
                });
            }


            @Override
            public void onResponse(Call call, final Response response) {
                Exception exception;
                try {
                    parse(response);
                    exception = null;
                } catch (Exception e) {
                    exception = e;
                }
                final Exception e = exception;
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onCompleted(e, t, response);
                    }
                });
            }

            private void onCompleted(Exception e, T t, Response response) {
                if (callback instanceof ResponseFutureCallback) {
                    ((ResponseFutureCallback) callback).onCompleted(e, t, response);
                } else
                    callback.onCompleted(e, t);
            }


        });

    }

    public T get() throws Exception {
        return parse(prepare().execute());
    }

    protected T parse(Response response) throws Exception {
        if (a.isString) {
            t = (T) response.body().string();
        } else {
            t = (T) a.gson.fromJson(response.body().charStream(), aClass);
        }

        try {
            response.body().close();
        } catch (Exception ignored) {
        }
        return t;


    }

    protected Call prepare() {
        RequestBody requestBody = RequestBody.create(A.JSON, a.getRequestBody() == null ? "" : a.getRequestBody().toString());
        if (a.getParts() != null) {
            if (a.getParts().size() > 0) {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (Part part : a.getParts()) {

                    MediaType mediaType = MediaType.parse("application/octet-stream");
                    if (part.getFile() == null)
                        continue;
                    RequestBody body = RequestBody.create(mediaType, part.getFile());
                    builder = builder.addFormDataPart(part.getKey(), part.getFile().getName(), body);
                }
                if (a.getRequestBody() != null)
                    builder.addPart(requestBody);
                requestBody = builder.build();
            }
        }
        Request.Builder builder = new Request.Builder()
                .url(a.getUrl());

        if (a.headers != null)
            builder.headers(a.headers);

        Request request = a.method == null ? (
                a.getRequestBody() == null ?
                        builder.get().build() :
                        builder.post(requestBody).build()
        ) : builder.method(a.method.toUpperCase(), requestBody).build();
        a.request = request;


        return a.client.newCall(request);
    }


    Response execute() throws IOException {
        return prepare().execute();
    }
}
