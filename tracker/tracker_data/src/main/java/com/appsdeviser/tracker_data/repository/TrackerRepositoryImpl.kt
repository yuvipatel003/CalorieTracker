package com.appsdeviser.tracker_data.repository

import com.appsdeviser.tracker_data.local.TrackerDao
import com.appsdeviser.tracker_data.mapper.toTrackableFood
import com.appsdeviser.tracker_data.mapper.toTrackedFood
import com.appsdeviser.tracker_data.mapper.toTrackedFoodEntity
import com.appsdeviser.tracker_data.remote.OpenFoodApi
import com.appsdeviser.tracker_domain.model.TrackableFood
import com.appsdeviser.tracker_domain.model.TrackedFood
import com.appsdeviser.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products.filter {
                    val calculatedTotalCalories =
                        it.nutriments.carbohydrates100g * 4f +
                                it.nutriments.proteins100g * 4f +
                                it.nutriments.fat100g * 9f
                    val lowerBound = calculatedTotalCalories * 0.98f
                    val upperBound = calculatedTotalCalories * 1.02f
                    it.nutriments.energyKcal100g in lowerBound..upperBound
                }.mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(trackedFoodEntity = food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(trackedFoodEntity = food.toTrackedFoodEntity())
    }

    override fun getFoodsDetailForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.mapNotNull { it.toTrackedFood() }
        }
    }
}