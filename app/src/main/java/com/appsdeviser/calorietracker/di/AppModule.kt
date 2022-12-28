package com.appsdeviser.calorietracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.appsdeviser.core.data.preferences.DefaultPreferences
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.core.domain.use_case.FilterOutDigits
import com.appsdeviser.onboarding_domain.use_case.ValidateNutrients
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences{
        return application.getSharedPreferences(Preferences.KEY_PREFERENCE_NAME, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPref = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFilterOutDigitUseCase(): FilterOutDigits {
        return FilterOutDigits()
    }

}