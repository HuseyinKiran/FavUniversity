package com.huseyinkiran.favuniversities.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huseyinkiran.favuniversities.model.University

@Database(entities = [University::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {

    abstract fun universityDao(): UniversityDAO

}

