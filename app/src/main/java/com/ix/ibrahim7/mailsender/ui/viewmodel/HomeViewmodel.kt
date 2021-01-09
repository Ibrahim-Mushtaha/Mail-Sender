package com.ix.ibrahim7.mailsender.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.mailsender.repository.EmailRepository
import com.ix.ibrahim7.mailsender.model.Message
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

    fun insertMessage(message: Message) {
        uiScope.launch {
            mRepository!!.insertMessage(message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}