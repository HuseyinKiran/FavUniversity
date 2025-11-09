package com.huseyinkiran.favuniversities.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.huseyinkiran.favuniversities.domain.use_case.UniversityUseCases
import com.huseyinkiran.favuniversities.presentation.mapper.toDomain
import com.huseyinkiran.favuniversities.presentation.mapper.toUI
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val _expandedCityNames = MutableStateFlow<Set<String>>(emptySet())
    val expandedCityNames: StateFlow<Set<String>> = _expandedCityNames

    private val _expandedUniversityNames = MutableStateFlow<Set<String>>(emptySet())
    val expandedUniversityNames: StateFlow<Set<String>> = _expandedUniversityNames

    private val basePagingFlow = useCases
        .getCityPagingFlowUseCase()
        .map { paging -> paging.map { it.toUI() } }
        .cachedIn(viewModelScope)

    private val favoriteNamesFlow = useCases.getAllFavoritesUseCase()
        .map { list -> list.map { it.name }.toSet() }

    val uiPagingFlow = basePagingFlow
        .combine(favoriteNamesFlow) { paging, favs ->
            paging.map { city ->
                city.copy(
                    universities = city.universities.map { university ->
                        university.copy(isFavorite = university.name in favs)
                    }
                )
            }
        }
        .combine(expandedCityNames) { paging, expanded ->
            paging.map { city -> city.copy(isExpanded = city.name in expanded) }
        }
        .combine(expandedUniversityNames) { paging, expanded ->
            paging.map { city ->
                city.copy(universities = city.universities.map { university ->
                    university.copy(isExpanded = university.name in expanded)
                })
            }
        }

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