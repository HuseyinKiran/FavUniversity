package com.huseyinkiran.favuniversities.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.huseyinkiran.favuniversities.MainCoroutineRule
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.model.University
import com.huseyinkiran.favuniversities.repository.FakeUniversityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle

@ExperimentalCoroutinesApi
class UniversityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeUniversityRepository
    private lateinit var viewModel: UniversityViewModel

    @Before
    fun setup() {
        repository = FakeUniversityRepository()
        viewModel = UniversityViewModel(repository)
    }

    @Test
    fun upsertUniversityTest() = runTest {

        val university = University(
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

        val university = University(
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

    @Test
    fun `isUniversityExpandable should return false when all fields are dash`() {

        val university = University(
            id = 1,
            name = "Test University",
            address = "-",
            email = "-",
            fax = "-",
            phone = "-",
            rector = "-",
            website = "-",
        )

        val notExpandable = viewModel.isUniversityExpandable(university)
        assertThat(notExpandable).isFalse()

    }

    @Test
    fun `isUniversityExpandable should return true when university has valid fields`() {

        val university = University(
            id = 1,
            name = "Test University",
            address = "123 Test Street",
            email = "info@testuniversity.edu",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        val expandable = viewModel.isUniversityExpandable(university)
        assertThat(expandable).isTrue()

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
        val afterCollapsed = viewModel.expandedUniversities.getOrAwaitValue()
        assertThat(afterCollapsed).doesNotContain(universityName)

    }

}