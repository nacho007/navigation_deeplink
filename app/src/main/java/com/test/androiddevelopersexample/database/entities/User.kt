package com.test.androiddevelopersexample.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
@Entity
data class User(
    @PrimaryKey val login: String,
    @ColumnInfo(name = "avatar_url") val avatar_url: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "blog") val blog: String,
    var lastRefresh: Date
)
