package com.ix.ibrahim7.mailsender.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.model.Users

@Database(entities = [Category::class,Users::class,Message::class], version = 4,exportSchema = false)
abstract class EmailDatabase : RoomDatabase() {

    abstract val taskDao : EmailDao

    companion object{

        private var INSTANCE: EmailDatabase? = null

        fun getInstance(activity: Application): EmailDatabase {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        activity,
                        EmailDatabase::class.java,
                        "Email_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        fun CreateBuilder(context: Context) = Room.databaseBuilder(context.applicationContext,
            EmailDatabase::class.java,"email.db").build()
    }







}