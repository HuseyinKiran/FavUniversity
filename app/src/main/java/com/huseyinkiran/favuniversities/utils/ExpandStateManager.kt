package com.huseyinkiran.favuniversities.utils


object ExpandStateManager {

    val expandedProvinces = mutableMapOf<String, Boolean>()
    val homeExpandedUniversities = mutableMapOf<String, Boolean>()
    val favoritesExpandedUniversities = mutableMapOf<String, Boolean>()

    fun collapseUniversity(name: String) {
        favoritesExpandedUniversities[name] = false
    }

}