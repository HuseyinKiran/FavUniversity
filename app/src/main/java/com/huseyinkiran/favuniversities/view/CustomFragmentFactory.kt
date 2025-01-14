package com.huseyinkiran.favuniversities.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.huseyinkiran.favuniversities.adapter.UniversityAdapter
import javax.inject.Inject

class CustomFragmentFactory @Inject constructor(

) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            HomeFragment::class.java.name -> HomeFragment()
            FavoritesFragment::class.java.name -> FavoritesFragment()
            else -> super.instantiate(classLoader, className)
        }

    }

}