package com.huseyinkiran.favuniversities.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.huseyinkiran.favuniversities.domain.model.City
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import okio.IOException
import retrofit2.HttpException

class CityPagingSource(
    private val repository: UniversityRepository
) : PagingSource<Int, City>() {

    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        val pageIndex = params.key ?: PAGE_INDEX

        return try {
            val cities = repository.getCities(pageIndex)

            LoadResult.Page(
                data = cities,
                prevKey = if (pageIndex == PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (cities.isEmpty()) null else pageIndex + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
        const val PAGE_INDEX = 1

    }
}