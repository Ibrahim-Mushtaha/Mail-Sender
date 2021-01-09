package com.ix.ibrahim7.mailsender.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ix.ibrahim7.mailsender.util.Constant.DURATION
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val liveData: LiveData<SplashState>
        get() = mutableLiveData

    private val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            delay(DURATION.toLong())
            mutableLiveData.postValue(SplashState.MainActivity)
        }
    }


}

sealed class SplashState {
    object MainActivity : SplashState()
}


