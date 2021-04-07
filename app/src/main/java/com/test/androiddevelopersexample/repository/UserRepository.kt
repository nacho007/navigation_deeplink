package com.test.androiddevelopersexample.repository

import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import com.test.androiddevelopersexample.api.WebService
import com.test.androiddevelopersexample.database.dao.UserDao
import com.test.androiddevelopersexample.database.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Philippe on 02/03/2018.
 */
@Singleton
class UserRepository @Inject constructor(
    @Nullable private val webservice: WebService?,
    @Nullable private val userDao: UserDao?,
    @Nullable private val executor: Executor?
) {

    fun getUser(userLogin: String): LiveData<User> {
        refreshUser(userLogin) // try to refresh data if possible from Github Api
        return userDao?.load(userLogin)!! // return a LiveData directly from the database.

    }

    private fun refreshUser(userLogin: String) {
        executor?.execute {

            // Check if user was fetched recently
            val userExists =
                userDao?.hasUser(userLogin, getMaxRefreshTime(Date())) != null


            // If user have to be updated

            if (!userExists) {
                webservice?.getUser(userLogin)?.enqueue(object :
                    Callback<User?> {
                    override fun onResponse(
                        call: Call<User?>,
                        response: Response<User?>
                    ) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK")
                        //                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute {
                            val user =
                                response.body()
                            user?.lastRefresh = Date()
                            userDao?.save(user!!)
                        }
                    }

                    override fun onFailure(
                        call: Call<User?>,
                        t: Throwable
                    ) {
                        Log.e("TAG", "FAILURE!")
                    }
                })
            }
        }
    }

    private fun getMaxRefreshTime(currentDate: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = currentDate
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES)
        return cal.time
    }

    companion object {
        private const val FRESH_TIMEOUT_IN_MINUTES = 1
    }
}