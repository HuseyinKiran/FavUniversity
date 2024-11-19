package com.huseyinkiran.turkishuniversities.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.huseyinkiran.turkishuniversities.model.University

@Dao
interface UniversityDAO {

    @Upsert
    suspend fun upsertUniversity(university: University)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites() : LiveData<List<University>>

    @Delete
    suspend fun deleteUniversity(university: University)

    @Query("SELECT * FROM favorites WHERE name = :universityName")
    suspend fun getFavoriteByName(universityName: String): University?
}

