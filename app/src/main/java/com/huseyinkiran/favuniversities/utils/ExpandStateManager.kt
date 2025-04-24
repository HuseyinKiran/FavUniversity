package com.huseyinkiran.favuniversities.utils


object ExpandStateManager {

    val expandedCities = mutableMapOf<String, Boolean>()
    val homeExpandedUniversities = mutableMapOf<String, Boolean>()
    val favoritesExpandedUniversities = mutableMapOf<String, Boolean>()

    fun collapseUniversity(name: String) {
        favoritesExpandedUniversities[name] = false
    }

    fun clearExpandState() {
        expandedCities.clear()
        homeExpandedUniversities.clear()
        favoritesExpandedUniversities.clear()
    }

}