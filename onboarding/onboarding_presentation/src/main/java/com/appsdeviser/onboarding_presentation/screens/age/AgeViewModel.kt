package com.appsdeviser.onboarding_presentation.screens.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.core.domain.use_case.FilterOutDigits
import com.appsdeviser.core.navigation.Route
import com.appsdeviser.core.utils.Constant
import com.appsdeviser.core.utils.UiEvent
import com.appsdeviser.core.utils.UiText
import com.appsdeviser.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    var age by mutableStateOf(Constant.DEFAULT_AGE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEntered(age:String) {
        if(age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick(){
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.ResourceString(R.string.error_age_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))
        }
    }
}