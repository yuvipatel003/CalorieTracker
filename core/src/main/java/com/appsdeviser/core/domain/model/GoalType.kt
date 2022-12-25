package com.appsdeviser.core.domain.model

import com.appsdeviser.core.utils.Constant.GAIN_WEIGHT
import com.appsdeviser.core.utils.Constant.KEEP_WEIGHT
import com.appsdeviser.core.utils.Constant.LOSE_WEIGHT

sealed class GoalType(val name: String) {
    object LoseWeight : GoalType(LOSE_WEIGHT)
    object KeepWeight : GoalType(KEEP_WEIGHT)
    object GainWeight : GoalType(GAIN_WEIGHT)

    companion object {
        fun fromString(name: String): GoalType {
            return when (name) {
                LOSE_WEIGHT -> LoseWeight
                KEEP_WEIGHT -> KeepWeight
                GAIN_WEIGHT -> GainWeight
                else -> KeepWeight
            }
        }
    }
}
