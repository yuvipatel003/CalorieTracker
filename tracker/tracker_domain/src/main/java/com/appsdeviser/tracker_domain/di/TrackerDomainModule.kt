package com.appsdeviser.tracker_domain.di

import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.tracker_domain.repository.TrackerRepository
import com.appsdeviser.tracker_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackedFoodInsertUseCase = TrackedFoodInsertUseCase(repository),
            trackedFoodDeleteUseCase = TrackedFoodDeleteUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            getFoodsDetailForDate = GetFoodsDetailForDate(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }
}