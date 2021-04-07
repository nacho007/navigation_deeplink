package com.test.androiddevelopersexample.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.database.entities.User
import com.test.androiddevelopersexample.viewmodel.UserProfileViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainDaggerActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    private fun configureDagger() {
        AndroidInjection.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var viewModel: UserProfileViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.configureDagger()
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserProfileViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel?.user?.observe(this) { user ->
            updateUI(user)
        }
    }

    private fun updateUI(user: User?) {
        if (user != null) {
            Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform())
                .into(fragment_user_profile_image)

            fragment_user_profile_username.text = user.name
            fragment_user_profile_company.text = user.company
            fragment_user_profile_website.text = user.blog
        }
    }
}