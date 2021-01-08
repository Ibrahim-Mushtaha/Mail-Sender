package com.ix.ibrahim7.facebookintegration.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.facebookintegration.repository.EmailRepository
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Message
import kotlinx.coroutines.*

class HomeViewmodel(application: Application) :
    AndroidViewModel(application) {

        var MessageLiveData: LiveData<List<Message>>? = null
    var mRepository: EmailRepository? = null



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    init {
        mRepository =
            EmailRepository(
                application
            )
        MessageLiveData = mRepository?.getAllMessage()
    }

    override fun onCleared() {
        super.onCleared()
            viewModelJob.cancel()
    }

    fun insertMessage(message: Message) {
        uiScope.launch {
                mRepository!!.insertMessage(message)
        }
    }








}