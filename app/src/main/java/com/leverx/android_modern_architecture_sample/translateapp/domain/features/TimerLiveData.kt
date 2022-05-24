package com.example.academyhomework

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
class TimerLiveDataCustom(value:Int): MutableLiveData<Int>(value) {
    override fun onActive() {
        Log.d("TimerLiveData", "onActive() hasActiveObservers ${hasActiveObservers()}")
    }
    override fun onInactive() {
        Log.d("TimerLiveData", "onInactive() hasActiveObservers ${hasActiveObservers()}")
    }
    override fun removeObserver(observer: Observer<in Int>) {
        super.removeObserver(observer)
        Log.d("TimerLiveData", "removeObserver(${observer})")
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in Int>) {
        super.observe(owner, observer)
        Log.d("TimerLiveData", "observe(LifecycleOwner: $owner observer: $observer)")

    }
}

class TimerLiveData{
    companion object {

        private val scope = CoroutineScope(Dispatchers.IO)
        private var CheckJob:Job? = null
        private var _liveData: TimerLiveDataCustom = TimerLiveDataCustom(0)
        val liveData: LiveData<Int> get() = _liveData

        private var timer = 0
        fun start(){
            if(CheckJob == null) {
                CheckJob = scope.launch {
                    while (true) {
                        timer++
                        _liveData.postValue(timer)
                        Log.d("LIVEDATA", "timerStart: ${_liveData.value}")
                        delay(1000L)
                    }
                }
            }
        }


    }
}





