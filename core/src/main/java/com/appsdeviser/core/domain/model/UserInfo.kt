package com.appsdeviser.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val height: Int,
    val weight: Float,
    val goalType: GoalType,
    val activityLevel: ActivityLevel,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float
)
