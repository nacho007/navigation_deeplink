package com.test.androiddevelopersexample

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.test.androiddevelopersexample.ui.fragments.money.MoneyFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MoneyFragmentInstrumentedTest {

    @Test
    fun loadMoneyFragment() {
        // Setup
        launchFragmentInContainer<MoneyFragment>(
            fragmentArgs = null,
            factory = AppFragmentFactory()
        )

        onView(withId(R.id.title)).check(matches(isDisplayed()))
    }
}