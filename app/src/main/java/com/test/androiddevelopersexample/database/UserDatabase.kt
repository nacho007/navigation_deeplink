package com.test.androiddevelopersexample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.androiddevelopersexample.database.dao.UserDao
import com.test.androiddevelopersexample.database.entities.User

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
