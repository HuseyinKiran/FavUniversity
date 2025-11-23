package com.huseyinkiran.favuniversities.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyinkiran.favuniversities.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FavUniversities)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navController)

        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.favoritesFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val hideOn = setOf(
                R.id.splashFragment,
                R.id.websiteFragment
            )

            toolbar.isGone = dest.id in hideOn

            if (!toolbar.isGone) {
                toolbar.isTitleCentered = true
                toolbar.title = ""
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
