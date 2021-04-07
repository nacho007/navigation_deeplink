package com.test.androiddevelopersexample.viewmodel

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.test.androiddevelopersexample.database.entities.User
import com.test.androiddevelopersexample.repository.UserRepository
import javax.inject.Inject

/**
 * Created by ignaciodeandreisdenis on 7/20/20.
 */
class UserProfileViewModel @Inject constructor(
    @Nullable private val userRepository: UserRepository
) : ViewModel() {

    //    val userId : String = savedStateHandle["uid"] ?:
    val userId: String = "JakeWharton"

    val user: LiveData<User> = userRepository.getUser(userId)
}

