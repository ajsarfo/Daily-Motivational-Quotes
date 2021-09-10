package com.sarftec.dailymotivationalquotes.data.injection

import com.sarftec.dailymotivationalquotes.application.repository.ApplicationRepository
import com.sarftec.dailymotivationalquotes.data.repository.ApplicationRepositoryImpl
import com.sarftec.dailymotivationalquotes.data.repository.DomainRepositoryImpl
import com.sarftec.dailymotivationalquotes.domain.repository.DomainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {

    @Binds
    abstract fun domainRepository(repositoryImpl: DomainRepositoryImpl) : DomainRepository

    @Singleton
    @Binds
    abstract fun applicationRepository(repositoryImpl: ApplicationRepositoryImpl) : ApplicationRepository
}