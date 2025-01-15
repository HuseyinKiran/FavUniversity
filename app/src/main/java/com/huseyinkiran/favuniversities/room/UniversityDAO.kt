package com.huseyinkiran.favuniversities.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.huseyinkiran.favuniversities.model.University

@Dao
interface UniversityDAO {

    @Upsert
    suspend fun upsertUniversity(university: University)

    @Query("SELECT * FROM favorites WHERE isFavorite = 1")
    fun getAllFavorites() : LiveData<List<University>>

    @Delete
    suspend fun deleteUniversity(university: University)

    @Query("SELECT * FROM favorites WHERE name = :universityName")
    suspend fun getFavoriteByName(universityName: String): University?

}