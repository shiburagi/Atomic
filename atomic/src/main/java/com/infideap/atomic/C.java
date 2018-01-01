package com.infideap.atomic;

import java.lang.reflect.Type;

/**
 * Created by Shiburagi on 20/10/2016.
 */
public class C<T> extends AtomRequest<T> {
    private static final String TAG = C.class.getSimpleName();

    C(A a, Class<T> aClass) {
        super(a, aClass);

    }

    C(A a, Type t) {
        super(a, t);

    }


}
