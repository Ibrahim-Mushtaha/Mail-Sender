package com.ix.ibrahim7.facebookintegration.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Message
import com.ix.ibrahim7.facebookintegration.model.Users
import com.ix.ibrahim7.facebookintegration.model.entities.CategoryAndUsers

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



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessage(message: Message)

    @Query("SELECT * FROM Message_table WHERE date >= (1000 * strftime('%s', datetime('now', '-30 day'))) ORDER BY date DESC")
    fun getAllMessage(): LiveData<List<Message>>

 /*   @Query("SELECT * FROM task_table where title > :name")
    fun getTask(name:String): LiveData<List<Task>>?

    @Delete
    fun delete(task: Task)


    @Query("SELECT * from task_table WHERE type = :key")
    fun get(key: String): LiveData<List<Task>>?*/



}
