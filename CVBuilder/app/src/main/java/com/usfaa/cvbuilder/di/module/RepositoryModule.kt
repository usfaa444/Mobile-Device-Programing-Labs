package com.usfaa.cvbuilder.di.module

import com.usfaa.cvbuilder.ui.about.AboutRepository
import com.usfaa.cvbuilder.ui.about.AboutRepositoryContract
import com.usfaa.cvbuilder.ui.home.HomeRepository
import com.usfaa.cvbuilder.ui.home.HomeRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindHomeRepository(homeRepository: HomeRepository): HomeRepositoryContract

    @Binds
    @ViewModelScoped
    abstract fun bindAboutRepository(aboutRepository: AboutRepository): AboutRepositoryContract
}