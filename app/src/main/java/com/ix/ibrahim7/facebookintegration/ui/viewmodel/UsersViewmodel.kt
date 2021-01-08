package com.ix.ibrahim7.facebookintegration.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.facebookintegration.model.entities.CategoryAndUsers
import com.ix.ibrahim7.facebookintegration.repository.EmailRepository
import com.ix.ibrahim7.facebookintegration.model.Users
import kotlinx.coroutines.*

class UsersViewmodel(application: Application) :
    AndroidViewModel(application) {

    var CategoryAndUsersLiveData: LiveData<List<CategoryAndUsers>>? = null
    var mRepository: EmailRepository? = null



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    init {
        mRepository =
            EmailRepository(
                application
            )
    }

    fun getAllUser(id:String){
        CategoryAndUsersLiveData = mRepository!!.getAllCategoryAndUsers(id)
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
