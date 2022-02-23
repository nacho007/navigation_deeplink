package com.test.androiddevelopersexample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Test
    fun checkSplashFragment() {
        onView(withText("SPLASH")).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.title)).check(matches(isDisplayed()))
    }

}