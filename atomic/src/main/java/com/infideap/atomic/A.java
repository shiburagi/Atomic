package com.infideap.atomic;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class A {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = A.class.getSimpleName();
    private long connectTimeout = 1L;
    private long readTimeout = 3L;
    private TimeUnit connectTimeUnit = TimeUnit.MINUTES;
    private TimeUnit readTimeUnit = TimeUnit.MINUTES;


    static OkHttpClient globalClient;
    OkHttpClient client;

    static {
        globalClient = new OkHttpClient();
    }

    Context context;
    private String url;
    ProgressCallback downlaodProgressCallback;
    private StringBuilder requestBody;
    public Gson gson;
    ProgressCallback uploadProgressCallback;
    private List<Part> parts = new ArrayList<>();
    boolean isString;
    public Headers headers;
    String method;
    Request request;

    public A(Context context) {
        this.context = context;
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();


    }

    public A load(String url) {
        this.url = url;
        return this;
    }

    public A load(String url, String method) {
        this.url = url;
        this.method = method;
        return this;
    }

    public A progress(ProgressCallback progressCallback) {
        this.downlaodProgressCallback = progressCallback;
        return this;
    }


    public B write(File file) {
        B b = new B(this, file);
        return b;
    }

    public A setJsonPojoBody(Object request) {
        this.requestBody = new StringBuilder();
        gson.toJson(request, requestBody);
        return this;
    }

    public StringBuilder getRequestBody() {
        return requestBody;
    }

    public String getUrl() {
        return url;
    }

    public C<String> asString() {
        isString = true;
        return new C<String>(this, String.class);
    }

    public A uploadProgress(ProgressCallback progressCallback) {
        this.uploadProgressCallback = progressCallback;

        return this;
    }

    public A addMultipartParts(List<Part> parts) {
        this.parts = parts;
        this.method = Atom.POST_METHOD;
        return this;
    }

    public <T> C<T> as(Class<T> t) {
        C<T> c = new C<T>(this, t);
        isString = false;
        return c;
    }


    public List<Part> getParts() {
        return parts;
    }

    public A setMultipartFile(String key, File file) {

        if (method == null) {
            this.method = Atom.POST_METHOD;
        }
        parts.add(new FilePart(key, file));

        return this;
    }

    public A setHeader(Headers headers) {
        this.headers = headers;
        return this;
    }

    public A setConnectTimeout(long timeout, TimeUnit unit) {
        connectTimeout = timeout;
        connectTimeUnit = unit;

        return this;
    }

    public A setReadTimeout(long timeout, TimeUnit unit) {
        readTimeout = timeout;
        readTimeUnit = unit;

        return this;
    }

    void createClient() {
        OkHttpClient.Builder builder = globalClient.newBuilder()
                .connectTimeout(connectTimeout, connectTimeUnit)
                .readTimeout(readTimeout, readTimeUnit)
                .addNetworkInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        ProgressResponseBody progress =
                                new ProgressResponseBody(originalResponse.body(),
                                        downlaodProgressCallback);
                        return originalResponse.newBuilder()
                                .body(progress)
                                .build();
                    }
                });
        if (Atom.LOG_BODY) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(Atom.LOG_LEVEL);
            builder.addInterceptor(logging);
        }
        client = builder.build();
    }

}

