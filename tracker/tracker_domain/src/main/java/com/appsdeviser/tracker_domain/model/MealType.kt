package com.appsdeviser.tracker_domain.model

import com.appsdeviser.core.utils.Constant
import com.appsdeviser.core.utils.Constant.MEAL_TYPE_BREAKFAST
import com.appsdeviser.core.utils.Constant.MEAL_TYPE_DINNER
import com.appsdeviser.core.utils.Constant.MEAL_TYPE_LUNCH
import com.appsdeviser.core.utils.Constant.MEAL_TYPE_SNACK

sealed class MealType(val name: String) {
    object Breakfast : MealType(MEAL_TYPE_BREAKFAST)
    object Lunch : MealType(MEAL_TYPE_LUNCH)
    object Dinner : MealType(MEAL_TYPE_DINNER)
    object Snack : MealType(MEAL_TYPE_SNACK)

    companion object {
        fun fromString(name: String): MealType {
            return when (name.lowercase()) {
                MEAL_TYPE_BREAKFAST -> Breakfast
                MEAL_TYPE_LUNCH -> Lunch
                MEAL_TYPE_DINNER -> Dinner
                MEAL_TYPE_SNACK -> Snack
                else -> Breakfast
            }
        }
    }
}
