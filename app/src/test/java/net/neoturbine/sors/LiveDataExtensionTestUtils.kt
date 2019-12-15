package net.neoturbine.sors

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// This is based on approach in https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/test-common/java/com/android/example/github/util/LiveDataTestUtil.kt
val <T> LiveData<T>.observedValue : T
    get() {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                removeObserver(this)
            }
        }
        observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        @Suppress("UNCHECKED_CAST")
        return data[0] as T
    }