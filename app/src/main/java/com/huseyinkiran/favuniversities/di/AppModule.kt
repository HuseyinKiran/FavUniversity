package com.huseyinkiran.favuniversities.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.favuniversities.repository.local.UniversityLocalRepository
import com.huseyinkiran.favuniversities.repository.local.UniversityLocalRepositoryImpl
import com.huseyinkiran.favuniversities.repository.remote.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.repository.remote.UniversityRemoteRepositoryImpl
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
    fun provideLocalRepository(dao: UniversityDAO): UniversityLocalRepository =
        UniversityLocalRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideRemoteRepository(api: UniversityAPI): UniversityRemoteRepository =
        UniversityRemoteRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideUniversityRepository(
        localRepository: UniversityLocalRepository,
        remoteRepository: UniversityRemoteRepository
    ): UniversityRepository = UniversityRepositoryImpl(localRepository, remoteRepository)

}