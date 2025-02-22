package com.huseyinkiran.favuniversities.presentation

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.launchFragmentInHiltContainer
import com.huseyinkiran.favuniversities.presentation.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromHomeToFavorites() {

        val navController = Mockito.mock(NavController::class.java)
        TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(withId(R.id.homeFragment)).perform(click())

        Mockito.verify(navController)
            .navigate(HomeFragmentDirections.actionHomeFragmentToFavoritesFragment())

    }

}