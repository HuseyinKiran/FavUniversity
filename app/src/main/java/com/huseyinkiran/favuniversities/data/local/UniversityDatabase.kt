package com.huseyinkiran.favuniversities.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UniversityEntity::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {

    abstract fun universityDao(): UniversityDAO

}