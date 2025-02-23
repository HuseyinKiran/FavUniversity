package com.huseyinkiran.favuniversities.di

import android.content.Context
import androidx.room.Room
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversities.data.repository.UniversityLocalRepositoryImpl
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.data.repository.UniversityRemoteRepositoryImpl
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import com.huseyinkiran.favuniversities.data.repository.UniversityRepositoryImpl
import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.data.local.UniversityDatabase
import com.huseyinkiran.favuniversities.data.remote.UniversityAPI
import com.huseyinkiran.favuniversities.utils.PermissionRepository
import com.huseyinkiran.favuniversities.utils.Util.BASE_URL
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

    @[Singleton Provides]
    fun providePermissionRepository(): PermissionRepository = PermissionRepository()

}