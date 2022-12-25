package com.appsdeviser.calorietracker.navigation

import androidx.navigation.NavController
import com.appsdeviser.core.utils.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}