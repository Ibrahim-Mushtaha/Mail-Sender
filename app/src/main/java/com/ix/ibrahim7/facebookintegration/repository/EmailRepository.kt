package com.ix.ibrahim7.facebookintegration.repository

import android.app.Application
import com.ix.ibrahim7.facebookintegration.database.EmailDao
import com.ix.ibrahim7.facebookintegration.database.EmailDatabase
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Message
import com.ix.ibrahim7.facebookintegration.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailRepository(application: Application)  {


    var data: EmailDatabase?=null
    var mDao: EmailDao

    init {
        data =
            EmailDatabase.getInstance(
                application
            )
        mDao =data!!.taskDao
    }


    fun getAllCategory() = mDao.getAllCategory()
    fun getAllCategoryAndUsers(id: String) = mDao.getAllCategoryAndUsers(id)
    fun getAllMessage() = mDao.getAllMessage()


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

    suspend fun insertMessage(message: Message) {
        withContext(Dispatchers.IO){
            mDao.insertMessage(message)
        }
    }



}