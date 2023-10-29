package com.appsdeviser.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsdeviser.core.domain.use_case.FilterOutDigits
import com.appsdeviser.core.utils.UiEvent
import com.appsdeviser.core.utils.UiText
import com.appsdeviser.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map {
                        if(it.trackableFood == event.trackableFood) {
                            it.copy(amount = filterOutDigits(event.amount))
                        } else it
                    }
                )
            }
            is SearchEvent.OnQueryChange -> {
                state = state.copy(
                    query = event.query
                )
            }
            SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map{
                        if(it.trackableFood == event.trackableFood){
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoodList = emptyList()
            )

            trackerUseCases.searchFoodUseCase(query = state.query)
                .onSuccess{ list ->
                    state = state.copy(
                        isSearching = false,
                        trackableFoodList = list.map {
                            TrackableFoodUiState(it)
                        },
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                           UiText.ResourceString(com.appsdeviser.core.R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val selectedUiState =
                state.trackableFoodList.find { it.trackableFood == event.trackableFood }

            trackerUseCases.trackedFoodInsertUseCase(
                food = selectedUiState?.trackableFood ?: return@launch,
                amount = selectedUiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}