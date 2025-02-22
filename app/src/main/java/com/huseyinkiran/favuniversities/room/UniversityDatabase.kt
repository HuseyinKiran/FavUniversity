package com.huseyinkiran.favuniversities.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huseyinkiran.favuniversities.model.dto.UniversityDto

@Database(entities = [UniversityDto::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {

    abstract fun universityDao(): UniversityDAO

}