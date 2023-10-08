package com.appsdeviser.tracker_presentation.search

import com.appsdeviser.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val trackableFood: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)
