package com.appsdeviser.core.domain.preferences

import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.domain.model.Gender
import com.appsdeviser.core.domain.model.GoalType
import com.appsdeviser.core.domain.model.UserInfo

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age:Int)
    fun saveHeight(height:Int)
    fun saveWeight(weight:Float)
    fun saveGoalType(goalType: GoalType)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio:Float)
    fun saveFatRatio(ratio:Float)

    fun loadUserInfo(): UserInfo

    fun saveShouldShowOnBoarding(shouldShow:Boolean)
    fun loadShouldShowOnBoarding(): Boolean

    companion object {
        const val KEY_PREFERENCE_NAME = "shared_pref"
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_GOAL_TYPE = "goal_type"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
        const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
    }
}