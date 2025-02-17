package com.huseyinkiran.favuniversities.view

import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@HiltAndroidTest
@ExperimentalCoroutinesApi
class WebsiteFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationWebsiteToPopBackStack() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<WebsiteFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(withId(R.id.goBack)).perform(click())
        Mockito.verify(navController).popBackStack()

    }

}