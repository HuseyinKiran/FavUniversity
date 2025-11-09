package com.huseyinkiran.favuniversities.domain.use_case

import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import javax.inject.Inject

class GetCityPagingFlowUseCase @Inject constructor(
    private val repository: UniversityRepository
) {

    operator fun invoke() = repository.getCityPagingFlow()

}