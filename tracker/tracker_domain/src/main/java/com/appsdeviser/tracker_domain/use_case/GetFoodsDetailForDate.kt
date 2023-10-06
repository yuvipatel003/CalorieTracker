package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.tracker_domain.model.TrackedFood
import com.appsdeviser.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsDetailForDate(
    private val repository: TrackerRepository
) {
    operator fun invoke(
        date: LocalDate
    ): Flow<List<TrackedFood>> {
        return repository.getFoodsDetailForDate(
            localDate = date
        )
    }
}