package com.huseyinkiran.favuniversities.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.favuniversities.repository.UniversityRepository
import com.huseyinkiran.favuniversities.repository.UniversityRepositoryImpl
import com.huseyinkiran.favuniversities.room.UniversityDAO
import com.huseyinkiran.favuniversities.room.UniversityDatabase
import com.huseyinkiran.favuniversities.service.UniversityAPI
import com.huseyinkiran.favuniversities.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, UniversityDatabase::class.java, "UniversityDB"
    ).build()

    @Singleton
    @Provides
    fun provideUniversityAPI(): UniversityAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversityAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDao(database: UniversityDatabase) = database.universityDao()

    @Singleton
    @Provides
    fun provideRepository(dao: UniversityDAO, api: UniversityAPI) =
        UniversityRepositoryImpl(dao, api) as UniversityRepository

}