package com.huseyinkiran.favuniversities.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.favuniversities.data.local.UniversityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDao(database: UniversityDatabase) = database.universityDao()

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, UniversityDatabase::class.java, "UniversityDB"
    ).build()

}