package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.model.Province
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProvinceViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeUniversityRepository
    private lateinit var viewModel: ProvinceViewModel

    @Before
    fun setup() {
        repository = FakeUniversityRepository()
        viewModel = ProvinceViewModel(repository)
    }

    @Test
    fun `loadProvinces should update provinceList on success`() = runTest {

        val fakeProvinces = listOf(
            Province(id = 1, province = "Test Province 1", universities = emptyList()),
            Province(id = 2, province = "Test Province 2", universities = emptyList())
        )

        repository.setFakeProvinces(fakeProvinces)
        viewModel.loadProvinces()
        advanceUntilIdle()
        val afterAdd = viewModel.provinceList.getOrAwaitValue()
        assertThat(afterAdd).isEqualTo(fakeProvinces)
        assertThat(afterAdd).contains(Province(id = 1, province = "Test Province 1", universities = emptyList()))

    }

    @Test
    fun `checkProvinceExpansion should expand when if not expanded`() {

        val provinceName = "Test City"
        viewModel.checkProvinceExpansion(provinceName)
        val afterExpand = viewModel.expandedProvinces.getOrAwaitValue()
        assertThat(afterExpand).contains(provinceName)

    }

    @Test
    fun `checkProvinceExpansion should collapse if already expanded`() {

        val provinceName = "Test City"
        viewModel.checkProvinceExpansion(provinceName)
        viewModel.checkProvinceExpansion(provinceName)
        val afterCollapse = viewModel.expandedProvinces.getOrAwaitValue()
        assertThat(afterCollapse).doesNotContain(provinceName)

    }

    @Test
    fun `checkUniversityExpansion should expand if not expanded`() {

        val universityName = "Test University"
        viewModel.checkUniversityExpansion(universityName)
        val afterExpand = viewModel.expandedUniversities.getOrAwaitValue()
        assertThat(afterExpand).contains(universityName)

    }

    @Test
    fun `checkUniversityExpansion should collapse if already expanded`() {

        val universityName = "Test University"
        viewModel.checkUniversityExpansion(universityName)
        viewModel.checkUniversityExpansion(universityName)
        val afterCollapse = viewModel.expandedUniversities.getOrAwaitValue()
        assertThat(afterCollapse).doesNotContain(universityName)

    }

}