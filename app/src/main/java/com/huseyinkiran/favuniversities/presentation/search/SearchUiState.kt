package com.huseyinkiran.favuniversities.presentation.search

import com.huseyinkiran.favuniversities.presentation.model.UniversityUIModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val results: List<UniversityUIModel> = emptyList(),
    val errorMessage: String? = null
)