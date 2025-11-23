package com.huseyinkiran.favuniversities.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.huseyinkiran.favuniversities.domain.use_case.UniversityUseCases
import com.huseyinkiran.favuniversities.presentation.adapter.home.HomeItem
import com.huseyinkiran.favuniversities.presentation.mapper.toDomain
import com.huseyinkiran.favuniversities.presentation.mapper.toUI
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val _expandedCityNames = MutableStateFlow<Set<String>>(emptySet())
    private val _expandedUniversityNames = MutableStateFlow<Set<String>>(emptySet())

    private val basePagingFlow = useCases
        .getCityPagingFlowUseCase()
        .map { paging -> paging.map { it.toUI() } }
        .cachedIn(viewModelScope)

    private val favoriteNamesFlow = useCases
        .getAllFavoritesUseCase()
        .map { list -> list.map { it.name }.toSet() }

    val uiPagingFlow = combine(
        basePagingFlow,
        favoriteNamesFlow,
        _expandedCityNames,
        _expandedUniversityNames
    ) { paging, favs, expandedCities, expandedUnis ->
        paging.map { city ->

            val updatedUniversities = city.universities.map { university ->
                university.copy(
                    isFavorite = university.name in favs,
                    isExpanded = university.name in expandedUnis
                )
            }

            val isCityExpanded = city.name in expandedCities

            val items = mutableListOf<HomeItem>()
            items += HomeItem.CityHeader(
                cityId = city.id,
                cityName = city.name,
                isExpanded = isCityExpanded
            )

            if (isCityExpanded) {
                updatedUniversities.forEach { university ->
                    items += HomeItem.UniversityRow(
                        cityId = city.id,
                        university = university
                    )
                }
            }

            items
        }
    }
        .map { paging -> paging.flatMap { it } }
        .cachedIn(viewModelScope)

    fun toggleFavorite(university: UniversityUIModel) = viewModelScope.launch {
        useCases.toggleFavoriteUniversityUseCase(university.toDomain())
    }

    fun onCityExpanded(cityName: String) {
        _expandedCityNames.value = _expandedCityNames.value.toMutableSet().apply {
            if (!add(cityName)) remove(cityName)
        }
    }

    fun onUniversityExpanded(universityName: String) {
        _expandedUniversityNames.value = _expandedUniversityNames.value.toMutableSet().apply {
            if (!add(universityName)) remove(universityName)
        }
    }
}