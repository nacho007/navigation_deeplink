package com.test.androiddevelopersexample.di

import android.app.Application
import androidx.annotation.Nullable
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.androiddevelopersexample.api.WebService
import com.test.androiddevelopersexample.database.UserDatabase
import com.test.androiddevelopersexample.database.dao.UserDao
import com.test.androiddevelopersexample.di.module.ViewModelModule
import com.test.androiddevelopersexample.repository.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    // --- DATABASE INJECTION ---
    @Provides
    @Singleton
    fun provideDatabase(application: Application?): UserDatabase? {
        return Room.databaseBuilder(
            application?.applicationContext!!,
            UserDatabase::class.java, "MyDatabase.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase?): UserDao? {
        return database?.userDao()
    }


    // --- REPOSITORY INJECTION ---
    @Provides
    @Singleton
    fun provideExecutor(): Executor? {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        webservice: WebService?,
        userDao: UserDao?,
        executor: Executor?
    ): UserRepository? {
        return UserRepository(webservice, userDao, executor)
    }


    // --- NETWORK INJECTION ---
    private val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideGson(): Gson? {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson?): Retrofit? {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiWebservice(@Nullable restAdapter: Retrofit): WebService? {
        return restAdapter.create(WebService::class.java)
    }
}
