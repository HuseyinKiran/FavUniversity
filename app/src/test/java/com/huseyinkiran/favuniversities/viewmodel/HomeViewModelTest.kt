package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.data.remote.dto.CityDto
import com.huseyinkiran.favuniversities.presentation.home.HomeViewModel
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeUniversityRepository
    private lateinit var permission: PermissionRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        repository = FakeUniversityRepository()
        permission = PermissionRepository()
        viewModel = HomeViewModel(repository, permission)
    }

    @Test
    fun `loadCities should update cityList on success`() = runTest {

        val fakeCities = listOf(
            CityDto(id = 1, name = "Test City 1", universities = emptyList()),
            CityDto(id = 2, name = "Test City 2", universities = emptyList())
        )

        repository.setFakeCities(fakeCities)
        viewModel.loadCities()
        advanceUntilIdle()
        val afterAdd = viewModel.cityList.getOrAwaitValue()
        assertThat(afterAdd).isEqualTo(fakeCities)
        assertThat(afterAdd).contains(CityDto(id = 1, name = "Test City 1", universities = emptyList()))

    }

}