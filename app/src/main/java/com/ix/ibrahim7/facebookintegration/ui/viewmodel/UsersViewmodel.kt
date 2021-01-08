package com.ix.ibrahim7.facebookintegration.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.facebookintegration.database.EmailRepository
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Users
import kotlinx.coroutines.*

class UsersViewmodel(application: Application) :
    AndroidViewModel(application) {

        var UsersLiveData: LiveData<List<Users>>? = null
    var mRepository: EmailRepository? = null



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    init {
        mRepository = EmailRepository(application)
    }

    fun getAllUsers(id:String){
        UsersLiveData = mRepository!!.getAllUser(id)
    }

    fun insertUser(users: Users) {
        uiScope.launch {
                mRepository!!.insertUser(users)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }




}
