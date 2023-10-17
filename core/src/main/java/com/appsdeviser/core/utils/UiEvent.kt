package com.appsdeviser.core.utils

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}
