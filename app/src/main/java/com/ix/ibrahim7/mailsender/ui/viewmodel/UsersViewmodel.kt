package com.ix.ibrahim7.mailsender.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.mailsender.model.entities.CategoryAndUsers
import com.ix.ibrahim7.mailsender.repository.EmailRepository
import com.ix.ibrahim7.mailsender.model.Users
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

    fun updateUser(users: Users) {
        uiScope.launch {
                mRepository!!.updateUser(users)
        }
    }

    fun deleteUser(users: Users) {
        uiScope.launch {
                mRepository!!.deleteUser(users)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }




}
