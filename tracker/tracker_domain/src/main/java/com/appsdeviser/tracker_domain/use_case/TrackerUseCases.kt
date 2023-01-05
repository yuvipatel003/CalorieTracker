package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.tracker_domain.model.TrackedFood

data class TrackerUseCases(
    val trackedFoodInsertUseCase: TrackedFoodInsertUseCase,
    val trackedFoodDeleteUseCase: TrackedFoodDeleteUseCase,
    val searchFoodUseCase: SearchFoodUseCase,
    val getFoodsDetailForDate: GetFoodsDetailForDate,
    val calculateMealNutrients: CalculateMealNutrients
)
