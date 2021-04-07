package com.test.androiddevelopersexample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.test.androiddevelopersexample.database.entities.User
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE login = :userLogin")
    fun load(userLogin: String): LiveData<User>

    @Query("SELECT * FROM user WHERE login = :userLogin AND lastRefresh > :lastRefreshMax LIMIT 1")
    fun hasUser(userLogin: String?, lastRefreshMax: Date): User?
}
