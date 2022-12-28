package com.appsdeviser.core.utils

import android.content.Context

sealed class UiText {
    data class RemoteString(val text: String): UiText()
    data class ResourceString(val resId : Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is RemoteString -> text
            is ResourceString -> context.getString(resId)
        }
    }
}
