package com.appsdeviser.tracker_presentation

import com.appsdeviser.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object onNextDayClick: TrackerOverviewEvent()
    object onPreviousDayClick: TrackerOverviewEvent()
    data class onToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class onDeleteTrackedFood(val trackedFood: TrackedFood): TrackerOverviewEvent()
    data class onAddFoodClick(val meal: Meal): TrackerOverviewEvent()
}
