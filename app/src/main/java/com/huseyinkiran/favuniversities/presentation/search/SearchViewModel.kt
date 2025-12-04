package com.huseyinkiran.favuniversities.presentation.search

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
class SearchViewModel @Inject constructor(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val allUniversities = MutableStateFlow<List<UniversityUIModel>>(emptyList())
    private val _expandedUniversityNames = MutableStateFlow<Set<String>>(emptySet())
    private val _query = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

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

            if (queryText.length < 2) {
                emptyList()
            } else {
                updated.filter { it.name.contains(queryText, ignoreCase = true) }
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val uiState: StateFlow<SearchUiState> =
        combine(
            searchResults,
            _isLoading,
            _errorMessage
        ) { searchResults, isLoading, errorMessage ->
            SearchUiState(
                results = searchResults,
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchUiState()
        )

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val universityList = useCases.getAllUniversitiesUseCase()
                allUniversities.value = universityList.map { it.toUI() }
            } catch (e: Exception) {
                _errorMessage.value = "İnternet bağlantınızı kontrol ediniz."
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
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