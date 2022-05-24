package com.leverx.android_modern_architecture_sample.util.idlingResource

object Idling {
    private const val RESOURCE = "Idling"

    @JvmField
    val countingIdlingResource = IdlingResource(RESOURCE)

    fun startProcess() {
        countingIdlingResource.increment()
    }

    fun endProcess() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}
