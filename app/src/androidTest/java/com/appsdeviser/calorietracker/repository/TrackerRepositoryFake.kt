package com.appsdeviser.calorietracker.repository

import com.appsdeviser.tracker_domain.model.TrackableFood
import com.appsdeviser.tracker_domain.model.TrackedFood
import com.appsdeviser.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import kotlin.random.Random

class TrackerRepositoryFake: TrackerRepository {

    var shouldReturnError = false
    private val trackedFood = mutableListOf<TrackedFood>()
    var searchResult = listOf<TrackableFood>()

    private val getFoodsDetailsForDateFlow = MutableSharedFlow<List<TrackedFood>>(replay = 1)

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return if(shouldReturnError){
            Result.failure(Throwable())
        } else {
            Result.success(searchResult)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackedFood.add(food.copy(
            id = Random.nextInt()
        ))
        getFoodsDetailsForDateFlow.emit(trackedFood)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackedFood.remove(food)
        getFoodsDetailsForDateFlow.emit(trackedFood)
    }

    override fun getFoodsDetailForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return getFoodsDetailsForDateFlow
    }
}