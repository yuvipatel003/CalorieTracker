package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.tracker_domain.model.TrackedFood
import com.appsdeviser.tracker_domain.repository.TrackerRepository

class TrackedFoodDeleteUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        food: TrackedFood,
    ) {
        return repository.deleteTrackedFood(food = food)
    }
}