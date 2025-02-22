package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.model.dto.ProvinceDto
import com.huseyinkiran.favuniversities.presentation.home.HomeViewModel
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import com.huseyinkiran.favuniversities.repository.PermissionRepository
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
    fun `loadProvinces should update provinceList on success`() = runTest {

        val fakeProvinces = listOf(
            ProvinceDto(id = 1, name = "Test Province 1", universities = emptyList()),
            ProvinceDto(id = 2, name = "Test Province 2", universities = emptyList())
        )

        repository.setFakeProvinces(fakeProvinces)
        viewModel.loadProvinces()
        advanceUntilIdle()
        val afterAdd = viewModel.provinceList.getOrAwaitValue()
        assertThat(afterAdd).isEqualTo(fakeProvinces)
        assertThat(afterAdd).contains(ProvinceDto(id = 1, name = "Test Province 1", universities = emptyList()))

    }

}