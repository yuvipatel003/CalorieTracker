package com.appsdeviser.tracker_presentation.tracker_overview

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.core.navigation.Route
import com.appsdeviser.core.utils.UiEvent
import com.appsdeviser.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
): ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodForDateJob: Job? = null
    init {
        preferences.saveShouldShowOnBoarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent){
        when(event){
            is TrackerOverviewEvent.onAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.SEARCH
                                    + "/${event.meal.name}"
                                    +"/${state.date.dayOfMonth}"
                                    +"/${state.date.month}"
                                    +"/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverviewEvent.onDeleteTrackedFood -> {
                viewModelScope.launch {
                    trackerUseCases.trackedFoodDeleteUseCase(food = event.trackedFood)
                    refreshFoods()
                }
            }
            TrackerOverviewEvent.onNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            TrackerOverviewEvent.onPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverviewEvent.onToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if(it.name == event.meal.name){
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
        }
    }

    private fun refreshFoods(){
        getFoodForDateJob?.cancel()
        getFoodForDateJob = trackerUseCases
            .getFoodsDetailForDate(state.date)
            .onEach {trackedFoodList ->
                val nutrientsResult = trackerUseCases.calculateMealNutrients(trackedFoodList)
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFood = trackedFoodList,
                    meals = state.meals.map {
                        val mealNutrients = nutrientsResult.mealNutrients[it.mealType] ?: return@map it.copy(
                            carbs = 0,
                            protein = 0,
                            fat = 0,
                            calories = 0
                        )

                        it.copy(
                            carbs = mealNutrients.carbs,
                            protein = mealNutrients.protein,
                            fat = mealNutrients.fat,
                            calories = mealNutrients.calories,
                        )
                    }
                )

            }.launchIn(viewModelScope)
    }
}