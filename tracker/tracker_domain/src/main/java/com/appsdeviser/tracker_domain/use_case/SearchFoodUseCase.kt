package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.tracker_domain.model.TrackableFood
import com.appsdeviser.tracker_domain.repository.TrackerRepository

class SearchFoodUseCase (
    private val repository : TrackerRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ) : Result<List<TrackableFood>>{
        return if(query.isBlank()){
            Result.success(emptyList())
        } else {
            repository.searchFood(
                query = query.trim(),
                page = page,
                pageSize = pageSize
            )
        }
    }
}