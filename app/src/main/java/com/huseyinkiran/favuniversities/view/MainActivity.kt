package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyinkiran.favuniversities.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FavUniversities)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        //bottomNav.setupWithNavController(navController) favoritesFragment'a geçişte dalgalanma oluyprdu
        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }
}