package com.huseyinkiran.favuniversities.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.huseyinkiran.favuniversities.R
import com.huseyinkiran.favuniversities.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FavUniversities)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)

        val root = binding.main
        val toolbar = binding.toolbar
        val bottomBar = binding.customBottomBar

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, 0, systemBars.right, 0)

            binding.bgStatusBar.updateLayoutParams {
                height = systemBars.top
            }

            binding.bgNavigationBar.updateLayoutParams {
                height = systemBars.bottom
            }

            insets
        }

        setSupportActionBar(toolbar)

        val homeFragment = R.id.homeFragment
        val searchFragment = R.id.searchFragment
        val favoritesFragment = R.id.favoritesFragment
        val splashFragment = R.id.splashFragment
        val websiteFragment = R.id.websiteFragment

        val appBarConfig = AppBarConfiguration(
            setOf(homeFragment, searchFragment, favoritesFragment)
        )
        setupActionBarWithNavController(navController, appBarConfig)

        binding.tabHome.setOnClickListener {
            navigateBottomTab(homeFragment)
        }

        binding.tabSearch.setOnClickListener {
            navigateBottomTab(searchFragment)
        }

        binding.tabFavorites.setOnClickListener {
            navigateBottomTab(favoritesFragment)
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val hideOn = setOf(
                splashFragment, websiteFragment
            )

            toolbar.isGone = dest.id in hideOn
            bottomBar.isGone = dest.id in hideOn

            binding.bgStatusBar.setBackgroundColor(
                when (dest.id) {
                    splashFragment -> getColor(R.color.bg_splash)
                    else -> getColor(R.color.bg_toolbar)
                }
            )

            binding.bgNavigationBar.setBackgroundColor(
                when (dest.id) {
                    splashFragment -> getColor(R.color.bg_splash)
                    websiteFragment -> getColor(R.color.white)
                    else -> getColor(R.color.bg_app)
                }
            )

            binding.customBottomBar.isVisible = when (dest.id) {
                homeFragment, searchFragment, favoritesFragment -> true
                else -> false
            }

            if (!toolbar.isGone) {
                toolbar.isTitleCentered = true
                toolbar.title = when (dest.id) {
                    homeFragment -> getString(R.string.home_fragment_title)
                    searchFragment -> getString(R.string.search_fragment_title)
                    favoritesFragment -> getString(R.string.favorites_fragment_title)
                    else -> ""
                }
            }

            updateBottomBarSelection(dest.id)
        }
    }

    private fun navigateBottomTab(destinationId: Int) {
        val navOptions = navOptions {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
        }

        if (navController.currentDestination?.id != destinationId) {
            navController.navigate(destinationId, null, navOptions)
        }
    }

    private fun updateBottomBarSelection(destId: Int) = with(binding) {

        val homeFragment = R.id.homeFragment
        val searchFragment = R.id.searchFragment
        val favoritesFragment = R.id.favoritesFragment

        val isHome = destId == homeFragment
        val isSearch = destId == searchFragment
        val isFavorites = destId == favoritesFragment

        iconHome.isSelected = isHome
        iconSearch.isSelected = isSearch
        iconFavorites.isSelected = isFavorites

        if (isHome) iconHome.setColorFilter(getColor(R.color.bg_toolbar)) else iconHome.clearColorFilter()
        if (isSearch) iconSearch.setColorFilter(getColor(R.color.bg_toolbar)) else iconSearch.clearColorFilter()
        if (isFavorites) iconFavorites.setColorFilter(getColor(R.color.bg_toolbar)) else iconFavorites.clearColorFilter()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
