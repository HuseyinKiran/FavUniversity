package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.domain.model.toUIModel
import com.huseyinkiran.favuniversities.presentation.favorites.FavoritesViewModel
import com.huseyinkiran.favuniversities.utils.PermissionRepository
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

        val university = UniversityEntity(
            id = 1,
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        viewModel.upsertUniversity(university.toUIModel())
        advanceUntilIdle()
        val afterAdd = viewModel.getFavorites()
        assertThat(afterAdd).contains(university)

    }

    @Test
    fun deleteUniversityTest() = runTest {

        val university = UniversityEntity(
            id = 1,
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        viewModel.upsertUniversity(university.toUIModel())
        advanceUntilIdle()
        viewModel.deleteUniversity(university.toUIModel())
        advanceUntilIdle()
        val afterRemove = viewModel.getFavorites()
        assertThat(afterRemove).doesNotContain(university)

    }

}