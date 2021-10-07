package com.gan.interbook.di

import android.content.Context
import android.content.SharedPreferences
import com.gan.interbook.framework.preferences.PreferencesKeys
import com.gan.interbook.framework.preferences.SharedUserPreferences
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.framework.resources.implementation.AndroidResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAndroidResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider {
        return AndroidResourceProvider(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            PreferencesKeys.USER_PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideSharedUserPreferences(
        sharedPreferences: SharedPreferences
    ): SharedUserPreferences {
        return SharedUserPreferences(sharedPreferences)
    }
}