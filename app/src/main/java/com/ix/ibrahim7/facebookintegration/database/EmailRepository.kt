package com.ix.ibrahim7.facebookintegration.database

import android.app.Application
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailRepository(application: Application)  {


    var data: EmailDatabase?=null
    var mDao: EmailDao

    init {
        data = EmailDatabase.getInstance(application)
        mDao =data!!.taskDao
    }


    fun getAllCategory() = mDao.getAllCategory()
    fun getAllUser(id:String) = mDao.getAllUser(id)


     suspend fun insertCategory(category: Category) {
        withContext(Dispatchers.IO){
            mDao.insert(category)
        }
    }

    suspend fun updateCategory(category: Category){
        withContext(Dispatchers.IO){
            mDao.update(category)
        }
    }

    suspend fun deleteCategory(category: Category){
        withContext(Dispatchers.IO){
            mDao.delete(category)
        }
    }

    suspend fun insertUser(users: Users) {
        withContext(Dispatchers.IO){
            mDao.insertUser(users)
        }
    }

}