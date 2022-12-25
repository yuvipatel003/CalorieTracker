package com.appsdeviser.core.domain.model

import com.appsdeviser.core.utils.Constant.ACTIVITY_HIGH
import com.appsdeviser.core.utils.Constant.ACTIVITY_LOW
import com.appsdeviser.core.utils.Constant.ACTIVITY_MEDIUM
import com.appsdeviser.core.utils.Constant.FEMALE
import com.appsdeviser.core.utils.Constant.MALE
import com.appsdeviser.core.utils.Constant.UNKNOWN

sealed class ActivityLevel(val name: String) {
    object Low : ActivityLevel(ACTIVITY_LOW)
    object Medium : ActivityLevel(ACTIVITY_MEDIUM)
    object High : ActivityLevel(ACTIVITY_HIGH)

    companion object {
        fun fromString(name: String): ActivityLevel {
            return when (name) {
                ACTIVITY_LOW -> Low
                ACTIVITY_MEDIUM -> Medium
                ACTIVITY_HIGH -> High
                else -> Low
            }
        }
    }
}
