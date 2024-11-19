package com.huseyinkiran.turkishuniversities.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huseyinkiran.turkishuniversities.model.University

@Database(entities = [University::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {

    abstract fun getUniversityDao(): UniversityDAO

    companion object {
        @Volatile
        private var instance: UniversityDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UniversityDatabase::class.java,
                "university_db"
            ).build()
    }

}

