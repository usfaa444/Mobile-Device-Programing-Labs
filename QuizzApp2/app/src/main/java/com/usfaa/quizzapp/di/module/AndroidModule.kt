package com.usfaa.quizzapp.di.module

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AndroidModule {

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context) : Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    companion object {
        const val SHARED_PREFERENCE_FILE_NAME = "default_preferences"
    }
}