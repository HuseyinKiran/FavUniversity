package com.huseyinkiran.favuniversities.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDAO {

    @Upsert
    suspend fun upsertUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites() : Flow<List<UniversityEntity>>

    @Delete
    suspend fun deleteUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites WHERE name = :universityName")
    suspend fun getFavoriteByName(universityName: String): UniversityEntity?

}