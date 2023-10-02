package com.appsdeviser.core.data.preferences

import android.content.SharedPreferences
import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.domain.model.Gender
import com.appsdeviser.core.domain.model.GoalType
import com.appsdeviser.core.domain.model.UserInfo
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.core.utils.Constant

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPref.edit()
            .putString(Preferences.KEY_GOAL_TYPE, goalType.name)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPref.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        return UserInfo(
            gender = Gender.fromString(sharedPref.getString(Preferences.KEY_GENDER,null) ?: Constant.MALE),
            age = sharedPref.getInt(Preferences.KEY_AGE, -1),
            height = sharedPref.getInt(Preferences.KEY_HEIGHT, -1),
            weight = sharedPref.getFloat(Preferences.KEY_WEIGHT, -1f),
            goalType = GoalType.fromString(sharedPref.getString(Preferences.KEY_GOAL_TYPE, null) ?: Constant.KEEP_WEIGHT),
            activityLevel = ActivityLevel.fromString(sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null) ?: Constant.ACTIVITY_LOW),
            carbRatio = sharedPref.getFloat(Preferences.KEY_CARB_RATIO, -1f),
            proteinRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f),
            fatRatio = sharedPref.getFloat(Preferences.KEY_FAT_RATIO, -1f)
        )
    }

    override fun saveShouldShowOnBoarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return sharedPref.getBoolean(Preferences.KEY_AGE, true)
    }
}