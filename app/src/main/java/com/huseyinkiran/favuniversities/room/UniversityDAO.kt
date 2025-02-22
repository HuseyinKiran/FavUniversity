package com.huseyinkiran.favuniversities.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

@Dao
interface UniversityDAO {

    @Upsert
    suspend fun upsertUniversity(university: UniversityDto)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites() : LiveData<List<UniversityDto>>

    @Delete
    suspend fun deleteUniversity(university: UniversityDto)

    @Query("SELECT * FROM favorites WHERE name = :universityName")
    suspend fun getFavoriteByName(universityName: String): UniversityDto?

}