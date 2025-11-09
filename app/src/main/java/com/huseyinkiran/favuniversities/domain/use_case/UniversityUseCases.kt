package com.huseyinkiran.favuniversities.domain.use_case

import javax.inject.Inject

data class UniversityUseCases @Inject constructor(
    val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    val getCityPagingFlowUseCase: GetCityPagingFlowUseCase,
    val toggleFavoriteUniversityUseCase: ToggleFavoriteUniversityUseCase
)
