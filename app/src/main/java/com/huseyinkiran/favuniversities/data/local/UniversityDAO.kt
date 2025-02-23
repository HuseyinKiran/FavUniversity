package com.huseyinkiran.favuniversities.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UniversityDAO {

    @Upsert
    suspend fun upsertUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites() : LiveData<List<UniversityEntity>>

    @Delete
    suspend fun deleteUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites WHERE name = :universityName")
    suspend fun getFavoriteByName(universityName: String): UniversityEntity?

}