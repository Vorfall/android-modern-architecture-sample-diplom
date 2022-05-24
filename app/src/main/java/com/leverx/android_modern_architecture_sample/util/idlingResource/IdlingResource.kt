package com.leverx.android_modern_architecture_sample.util.idlingResource

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class IdlingResource(private val resourceName: String) :
    IdlingResource {
    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = resourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalStateException("Your counter has been used wrong")
        }
    }
}
