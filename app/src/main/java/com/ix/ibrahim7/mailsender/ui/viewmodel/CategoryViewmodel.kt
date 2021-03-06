package com.ix.ibrahim7.mailsender.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ix.ibrahim7.mailsender.repository.EmailRepository
import com.ix.ibrahim7.mailsender.model.Category
import kotlinx.coroutines.*

class CategoryViewmodel(application: Application) :
    AndroidViewModel(application) {

        var CategoryLiveData: LiveData<List<Category>>? = null
    var mRepository: EmailRepository? = null



    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    init {
        mRepository =
            EmailRepository(
                application
            )
        CategoryLiveData = mRepository?.getAllCategory()
    }

    override fun onCleared() {
        super.onCleared()
            viewModelJob.cancel()
    }

    fun insertCategory(category: Category) {
        uiScope.launch {
                mRepository!!.insertCategory(category)
        }
    }




    fun updateCategory(category: Category) {
        uiScope.launch {
                mRepository!!.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        uiScope.launch {
                mRepository!!.deleteCategory(category)
        }
    }








}