package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huseyinkiran.favuniversities.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FavUniversities)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}