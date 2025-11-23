package com.huseyinkiran.favuniversities.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversities.domain.use_case.UniversityUseCases
import com.huseyinkiran.favuniversities.presentation.mapper.toDomain
import com.huseyinkiran.favuniversities.presentation.mapper.toUI
import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val allUniversities = MutableStateFlow<List<UniversityUIModel>>(emptyList())
    private val _expandedUniversityNames = MutableStateFlow<Set<String>>(emptySet())
    private val _query = MutableStateFlow("")

    private val favoriteNamesFlow = useCases
        .getAllFavoritesUseCase()
        .map { favorites -> favorites.map { it.name }.toSet() }

    val searchResults: StateFlow<List<UniversityUIModel>> =
        combine(
            allUniversities,
            favoriteNamesFlow,
            _expandedUniversityNames,
            _query
        ) { all, favNames, expandedNames, queryText ->

            val updated = all.map { uni ->
                uni.copy(
                    isFavorite = uni.name in favNames,
                    isExpanded = uni.name in expandedNames
                )
            }

            if (queryText.length < 2) return@combine emptyList()

            updated.filter { it.name.contains(queryText, ignoreCase = true) }
        }.stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            val universityList = useCases.getAllUniversitiesUseCase()
            allUniversities.value = universityList.map { it.toUI() }
        }
    }

    fun searchUniversities(query: String) {
        _query.value = query
    }

    fun toggleFavorite(university: UniversityUIModel) = viewModelScope.launch {
        useCases.toggleFavoriteUniversityUseCase(university.toDomain())
    }

    fun onUniversityExpanded(universityName: String) {
        _expandedUniversityNames.value =
            _expandedUniversityNames.value.toMutableSet().apply {
                if (!add(universityName)) remove(universityName)
            }
    }

}