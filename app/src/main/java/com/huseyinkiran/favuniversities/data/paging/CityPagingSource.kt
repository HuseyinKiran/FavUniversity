package com.huseyinkiran.favuniversities.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.huseyinkiran.favuniversities.domain.model.CityUIModel
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository

class CityPagingSource(
    private val repository: UniversityRepository
): PagingSource<Int, CityUIModel>() {

    override fun getRefreshKey(state: PagingState<Int, CityUIModel>): Int? {
       return state.anchorPosition?.let {  anchorPosition ->
           val anchorPage = state.closestPageToPosition(anchorPosition)
           anchorPage?.nextKey?.minus(1) ?: anchorPage?.prevKey?.plus(1)
       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityUIModel> {
        val page = params.key ?: 1

        return try {
            val cities = repository.getUniversities(page)
            val totalPage = repository.getTotalPage()
            val isLastPage = page >= totalPage

            LoadResult.Page(
                data = cities,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isLastPage) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}