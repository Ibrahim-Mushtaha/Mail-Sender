package com.ix.ibrahim7.mailsender.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.model.Users
import com.ix.ibrahim7.mailsender.model.entities.CategoryAndUsers

@Dao
interface EmailDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category)

    @Update
    fun update(category: Category)



    @Query("SELECT * FROM category_table")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("SELECT * FROM category_table where id = :categoryid")
    fun getAllCategoryAndUsers(categoryid:String): LiveData<List<CategoryAndUsers>>


    @Delete
    fun delete(category: Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(users: Users)

    @Update
    fun updateUser(users: Users)

    @Delete
    fun deleteUser(users: Users)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessage(message: Message)

    @Query("SELECT * FROM Message_table WHERE date >= (1000 * strftime('%s', datetime('now', '-30 day'))) ORDER BY date DESC")
    fun getAllMessage(): LiveData<List<Message>>





}
