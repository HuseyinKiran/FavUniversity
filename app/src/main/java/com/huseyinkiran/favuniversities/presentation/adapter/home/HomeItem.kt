package com.huseyinkiran.favuniversities.presentation.adapter.home

import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

sealed class HomeItem {

    data class CityHeader(
        val cityId: Int,
        val cityName: String,
        val isExpanded: Boolean
    ) : HomeItem()

    data class UniversityRow(
        val cityId: Int,
        val university: UniversityUIModel
    ) : HomeItem()
}