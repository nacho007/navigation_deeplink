package com.test.androiddevelopersexample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.test.androiddevelopersexample.ui.fragments.login.LoginFragment
import com.test.androiddevelopersexample.ui.fragments.money.MoneyFragment

class AppFragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoneyFragment::class.java.name -> MoneyFragment()
            LoginFragment::class.java.name -> LoginFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}