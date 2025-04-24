package com.huseyinkiran.favuniversities.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.huseyinkiran.favuniversities.getOrAwaitValue
import com.huseyinkiran.favuniversities.data.local.UniversityEntity
import com.google.common.truth.Truth.assertThat
import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.data.local.UniversityDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class UniversityDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: UniversityDatabase

    private lateinit var dao: UniversityDAO

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.universityDao()
    }

    @Test
    fun getAllFavorites() = runTest {

        val university = UniversityEntity(
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        //val allFavorites = dao.getAllFavorites().getOrAwaitValue()
        //assertThat(allFavorites).isEmpty()

        dao.upsertUniversity(university)
        //val afterAdd = dao.getAllFavorites().getOrAwaitValue()
        //assertThat(afterAdd).isNotEmpty()
        //assertThat(afterAdd).contains(university)

    }

    @Test
    fun upsertUniversityTest() = runTest {

        val university = UniversityEntity(
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        dao.upsertUniversity(university)
        //val favorites = dao.getAllFavorites().getOrAwaitValue()
        //assertThat(favorites).contains(university)

    }

    @Test
    fun deleteUniversityTest() = runTest {

        val university = UniversityEntity(
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        dao.upsertUniversity(university)
        dao.deleteUniversity(university)
        //val favorites = dao.getAllFavorites().getOrAwaitValue()
        //assertThat(favorites).doesNotContain(university)

    }

    @Test
    fun getFavoriteByName() = runTest {

        val university = UniversityEntity(
            name = "Test University",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        val university2 = UniversityEntity(
            name = "Test University 2",
            address = "123 Test Street",
            fax = "123-456-7890",
            phone = "987-654-3210",
            rector = "Test President",
            website = "https://testuniversity.edu",
        )

        dao.upsertUniversity(university)
        dao.upsertUniversity(university2)
        val getFavoriteByName = dao.getFavoriteByName(university2.name)
        assertThat(getFavoriteByName?.name).isNotEqualTo(university.name)
        assertThat(getFavoriteByName?.name).isEqualTo(university2.name)

    }

}