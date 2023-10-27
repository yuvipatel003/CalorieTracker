package com.appsdeviser.calorietracker

import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.domain.model.Gender
import com.appsdeviser.core.domain.model.GoalType
import com.appsdeviser.core.domain.model.UserInfo
import com.appsdeviser.core.domain.preferences.Preferences

class PreferencesFake: Preferences {
    override fun saveGender(gender: Gender) {
        TODO("Not yet implemented")
    }

    override fun saveAge(age: Int) {
        TODO("Not yet implemented")
    }

    override fun saveHeight(height: Int) {
        TODO("Not yet implemented")
    }

    override fun saveWeight(weight: Float) {
        TODO("Not yet implemented")
    }

    override fun saveGoalType(goalType: GoalType) {
        TODO("Not yet implemented")
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        TODO("Not yet implemented")
    }

    override fun saveCarbRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun saveProteinRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun saveFatRatio(ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun loadUserInfo(): UserInfo {
        return UserInfo(
            gender = Gender.Male,
            age = 20,
            height = 172,
            weight = 75.0f,
            goalType = GoalType.KeepWeight,
            activityLevel = ActivityLevel.Low,
            carbRatio = 4.0f,
            proteinRatio = 4.0f,
            fatRatio = 2.0f
        )
    }

    override fun saveShouldShowOnBoarding(shouldShow: Boolean) {
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return false
    }
}