package com.huseyinkiran.favuniversities.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.favuniversities.data.local.UniversityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, UniversityDatabase::class.java)
            .allowMainThreadQueries().build()

}