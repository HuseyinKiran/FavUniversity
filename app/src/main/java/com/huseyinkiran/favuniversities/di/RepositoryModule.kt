package com.huseyinkiran.favuniversities.di

import com.huseyinkiran.favuniversities.data.local.UniversityDAO
import com.huseyinkiran.favuniversities.data.remote.UniversityAPI
import com.huseyinkiran.favuniversities.data.repository.UniversityLocalRepositoryImpl
import com.huseyinkiran.favuniversities.data.repository.UniversityRemoteRepositoryImpl
import com.huseyinkiran.favuniversities.data.repository.UniversityRepositoryImpl
import com.huseyinkiran.favuniversities.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversities.domain.repository.UniversityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(
        dao: UniversityDAO
    ): UniversityLocalRepository = UniversityLocalRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideRemoteRepository(
        api: UniversityAPI
    ): UniversityRemoteRepository = UniversityRemoteRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUniversityRepository(
        localRepository: UniversityLocalRepository,
        remoteRepository: UniversityRemoteRepository
    ): UniversityRepository = UniversityRepositoryImpl(localRepository, remoteRepository)

}