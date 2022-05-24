package com.leverx.android_modern_architecture_sample

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.leverx.android_modern_architecture_sample.ui.main.view.MainActivity
import com.leverx.android_modern_architecture_sample.util.idlingResource.Idling
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InstrumentalTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(Idling.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(Idling.countingIdlingResource)
        print("Finished testing")
    }

    @Test
    fun test_scrollTo() {
        scrollTo()
    }

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        Thread.sleep(1000)
        onView(withId(R.id.news)).check(matches(isDisplayed()))

    }

    @Test
    fun test_isListFragmentVisible_checkLike() {
        onView(withId(R.id.likeNews)).perform(click())
    }

    @Test
    fun test_isListFragmentVisible_checkToolBar() {
        onView(withId(R.id.text_in_toolbar_mein_screen)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isListFragmentVisible() {

        onView(((withId(R.id.news)))).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
    }

}
