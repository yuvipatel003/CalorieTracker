package com.appsdeviser.tracker_domain.use_case

import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.domain.model.Gender
import com.appsdeviser.core.domain.model.GoalType
import com.appsdeviser.core.domain.model.UserInfo
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.tracker_domain.model.MealType
import com.appsdeviser.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

/**
 *  Unit test
 */
class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setup(){
        val preferences = mockk<Preferences>()
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            height = 172,
            weight = 72.0f,
            goalType =GoalType.KeepWeight,
            activityLevel = ActivityLevel.Medium,
            carbRatio = 4.0f,
            proteinRatio = 3.0f,
            fatRatio = 3.0f
        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    private fun generateTrackedFood(): List<TrackedFood> {
        return (1..30).map {
            TrackedFood(
                name = "Random",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                imageUrl = null,
                mealType = MealType.fromString(
                    listOf(
                        MealType.Breakfast.name,
                        MealType.Lunch.name,
                        MealType.Dinner.name,
                        MealType.Snack.name
                    ).random()
                ),
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000),
                id = null
            )
        }
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)
        val actualResult = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }
        val expectedResult = trackedFood
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Carbs for dinner properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)
        val actualResult = result.mealNutrients.values
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.carbs }
        val expectedResult = trackedFood
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.carbs }

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Fat for lunch properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)
        val actualResult = result.mealNutrients.values
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.fat }
        val expectedResult = trackedFood
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.fat }

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Calories Fat Carbs Protein for lunch properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)

        val actualCalories = result.mealNutrients.values
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.calories }
        val expectedCalories = trackedFood
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.calories }
        assertThat(actualCalories).isEqualTo(expectedCalories)

        val actualFat = result.mealNutrients.values
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.fat }
        val expectedFat = trackedFood
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.fat }
        assertThat(actualFat).isEqualTo(expectedFat)

        val actualCarbs = result.mealNutrients.values
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFood
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.carbs }
        assertThat(actualCarbs).isEqualTo(expectedCarbs)

        val actualProtein = result.mealNutrients.values
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.protein }
        val expectedProtein = trackedFood
            .filter { it.mealType == MealType.Lunch }
            .sumOf { it.protein }
        assertThat(actualProtein).isEqualTo(expectedProtein)
    }

    @Test
    fun `Calories Fat Carbs Protein for breakfast properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)

        val actualCalories = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }
        val expectedCalories = trackedFood
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.calories }
        assertThat(actualCalories).isEqualTo(expectedCalories)

        val actualFat = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.fat }
        val expectedFat = trackedFood
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.fat }
        assertThat(actualFat).isEqualTo(expectedFat)

        val actualCarbs = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFood
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.carbs }
        assertThat(actualCarbs).isEqualTo(expectedCarbs)

        val actualProtein = result.mealNutrients.values
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.protein }
        val expectedProtein = trackedFood
            .filter { it.mealType == MealType.Breakfast }
            .sumOf { it.protein }
        assertThat(actualProtein).isEqualTo(expectedProtein)
    }

    @Test
    fun `Calories Fat Carbs Protein for dinner properly calculated`() {
        val trackedFood = generateTrackedFood()
        val result = calculateMealNutrients(trackedFoods = trackedFood)

        val actualCalories = result.mealNutrients.values
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.calories }
        val expectedCalories = trackedFood
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.calories }
        assertThat(actualCalories).isEqualTo(expectedCalories)

        val actualFat = result.mealNutrients.values
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.fat }
        val expectedFat = trackedFood
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.fat }
        assertThat(actualFat).isEqualTo(expectedFat)

        val actualCarbs = result.mealNutrients.values
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.carbs }
        val expectedCarbs = trackedFood
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.carbs }
        assertThat(actualCarbs).isEqualTo(expectedCarbs)

        val actualProtein = result.mealNutrients.values
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.protein }
        val expectedProtein = trackedFood
            .filter { it.mealType == MealType.Dinner }
            .sumOf { it.protein }
        assertThat(actualProtein).isEqualTo(expectedProtein)
    }
}