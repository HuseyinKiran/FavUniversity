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

    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAllFavorites() : Flow<List<UniversityEntity>>

    @Delete
    suspend fun deleteUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites WHERE id = :universityId")
    suspend fun getFavoriteById(universityId: Int): UniversityEntity?

}