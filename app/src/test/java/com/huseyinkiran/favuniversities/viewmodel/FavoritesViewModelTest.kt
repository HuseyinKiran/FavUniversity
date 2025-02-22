package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.model.dto.UniversityDto
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.presentation.favorites.FavoritesViewModel
import com.huseyinkiran.favuniversities.repository.PermissionRepository
import kotlinx.coroutines.test.advanceUntilIdle

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeUniversityRepository
    private lateinit var permission: PermissionRepository
    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setup() {
        repository = FakeUniversityRepository()
        permission = PermissionRepository()
        viewModel = FavoritesViewModel(repository, permission)
    }

    @Test
    fun upsertUniversityTest() = runTest {

        val university = UniversityDto(
            id = 1,
            name = "Test University",
            address = "123 Test Street",
            email = "info@testuniversity.edu",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        viewModel.upsertUniversity(university)
        advanceUntilIdle()
        val afterAdd = viewModel.favoriteUniversities.getOrAwaitValue()
        assertThat(afterAdd).contains(university)

    }

    @Test
    fun deleteUniversityTest() = runTest {

        val university = UniversityDto(
            id = 1,
            name = "Test University",
            address = "123 Test Street",
            email = "info@testuniversity.edu",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        viewModel.upsertUniversity(university)
        advanceUntilIdle()
        viewModel.deleteUniversity(university)
        advanceUntilIdle()
        val afterRemove = viewModel.favoriteUniversities.getOrAwaitValue()
        assertThat(afterRemove).doesNotContain(university)

    }

}