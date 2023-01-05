package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.domain.model.Gender
import com.appsdeviser.core.domain.model.GoalType
import com.appsdeviser.core.domain.model.UserInfo
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.tracker_domain.model.MealType
import com.appsdeviser.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences,
) {

    operator fun invoke(
        trackedFoods: List<TrackedFood>
    ): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                MealNutrients (
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()

        val caloriesGoal = dailyCaloriesRequirement(userInfo)
        val carbsGoal = (caloriesGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloriesGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloriesGoal * userInfo.fatRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloriesGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    /**
     *
     */
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender){
            is Gender.Male -> {
                (66.47f + 13.75f + userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female -> {
                (665.09f + 9.56f + userInfo.weight +
                        1.84f * userInfo.height - 4.67f * userInfo.age).roundToInt()
            }
            else -> {
                (66.47f + 13.75f + userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
        }
    }

    /**
     *
     */
    private fun dailyCaloriesRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel){
            ActivityLevel.High -> 1.4f
            ActivityLevel.Low -> 1.2f
            ActivityLevel.Medium -> 1.3f
        }
        val caloriesExtra = when(userInfo.goalType){
            GoalType.GainWeight -> 500
            GoalType.KeepWeight -> 0
            GoalType.LoseWeight -> -500
        }
        return (bmr(userInfo) * activityFactor + caloriesExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}