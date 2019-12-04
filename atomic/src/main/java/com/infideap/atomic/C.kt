package com.infideap.atomic

import com.infideap.atomic.C
import java.lang.reflect.Type

/**
 * Created by Shiburagi on 20/10/2016.
 */
class C<T> internal constructor(a: A, aClass: Class<T>?) : AtomRequest<T>(a, aClass) {
    //    internal constructor(a: A, t: Type) : super(a, t) {}

    companion object {
        private val TAG = C::class.java.simpleName
    }
}