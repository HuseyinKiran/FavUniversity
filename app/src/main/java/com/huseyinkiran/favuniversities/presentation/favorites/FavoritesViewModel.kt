package com.huseyinkiran.favuniversities.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.domain.use_case.UniversityUseCases
import com.huseyinkiran.favuniversities.presentation.mapper.toDomain
import com.huseyinkiran.favuniversities.presentation.mapper.toUI
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val _expandedFavUniversities = MutableStateFlow<Set<String>>(emptySet())
    val expandedFavUnis: StateFlow<Set<String>> = _expandedFavUniversities

    val favorites: StateFlow<List<UniversityUIModel>> =
        useCases.getAllFavoritesUseCase()
            .map { list -> list.map { it.toUI() } }
            .combine(expandedFavUnis) { list, expanded ->
                list.map { university ->
                    university.copy(isExpanded = university.name in expanded)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun toggleFavorite(university: UniversityUIModel) = viewModelScope.launch {
        useCases.toggleFavoriteUniversityUseCase(university.toDomain())
    }

    fun onFavUniversityExpanded(name: String) {
        _expandedFavUniversities.value = _expandedFavUniversities.value.toMutableSet().apply {
            if (!add(name)) remove(name)
        }
    }

}