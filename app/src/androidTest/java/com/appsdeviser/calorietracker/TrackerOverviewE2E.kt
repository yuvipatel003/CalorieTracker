package com.appsdeviser.calorietracker

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appsdeviser.calorietracker.navigation.Route
import com.appsdeviser.calorietracker.repository.TrackerRepositoryFake
import com.appsdeviser.calorietracker.ui.theme.CalorieTrackerTheme
import com.appsdeviser.core.domain.use_case.FilterOutDigits
import com.appsdeviser.tracker_domain.model.TrackableFood
import com.appsdeviser.tracker_domain.use_case.CalculateMealNutrients
import com.appsdeviser.tracker_domain.use_case.GetFoodsDetailForDate
import com.appsdeviser.tracker_domain.use_case.SearchFoodUseCase
import com.appsdeviser.tracker_domain.use_case.TrackedFoodDeleteUseCase
import com.appsdeviser.tracker_domain.use_case.TrackedFoodInsertUseCase
import com.appsdeviser.tracker_domain.use_case.TrackerUseCases
import com.appsdeviser.tracker_presentation.search.SearchScreen
import com.appsdeviser.tracker_presentation.search.SearchViewModel
import com.appsdeviser.tracker_presentation.tracker_overview.TrackerOverviewScreen
import com.appsdeviser.tracker_presentation.tracker_overview.TrackerOverviewViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.InternalPlatformDsl.toStr
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var preferencesFake: PreferencesFake
    private lateinit var repositoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var searchFoodUseCase: SearchFoodUseCase
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        preferencesFake = PreferencesFake()
        repositoryFake = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackedFoodInsertUseCase = TrackedFoodInsertUseCase(
                repository = repositoryFake
            ), trackedFoodDeleteUseCase = TrackedFoodDeleteUseCase(
                repository = repositoryFake
            ), searchFoodUseCase = SearchFoodUseCase(repository = repositoryFake), getFoodsDetailForDate = GetFoodsDetailForDate(
                repository = repositoryFake
            ), calculateMealNutrients = CalculateMealNutrients(
                preferences = preferencesFake
            )
        )
        searchFoodUseCase = SearchFoodUseCase(repositoryFake)
        trackerOverviewViewModel = TrackerOverviewViewModel(preferencesFake, trackerUseCases)
        searchViewModel = SearchViewModel(trackerUseCases, FilterOutDigits())

        composeRule.activity.setContent {
            CalorieTrackerTheme {
                navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.TRACKER_OVERVIEW,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, dayOfMonth, month, year ->
                                navController.navigate(
                                    Route.SEARCH
                                            + "/${mealName}"
                                            + "/${dayOfMonth}"
                                            + "/${month}"
                                            + "/${year}"
                                )
                            }, viewModel = trackerOverviewViewModel)
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                },
                                viewModel = searchViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientsProperlyCalculated(){
        repositoryFake.searchResult = listOf(
                TrackableFood(
                    name = "Apple",
                    imageUrl = null,
                    caloriesPer100g = 150,
                    carbsPer100g = 50,
                    proteinPer100g = 5,
                    fatPer100g = 1
                )
        )

        val addedAmount = 150
        val expectedCalories = (1.5f * 150).roundToInt()
        val expectedCarbs = (1.5f * 50).roundToInt()
        val expectedProtein = (1.5f * 5).roundToInt()
        val expectedFat = (1.5f * 1).roundToInt()

        composeRule
            .onNodeWithText("Add Breakfast")
            .assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription("Breakfast")
            .performClick()
        composeRule
            .onNodeWithText("Add Breakfast")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Add Breakfast")
            .performClick()
        assertThat(navController
            .currentDestination
            ?.route
            ?.startsWith(Route.SEARCH)).isTrue()

        composeRule
            .onNodeWithTag("search_text_field")
            .assertIsDisplayed()
        composeRule
            .onNodeWithTag("search_text_field")
            .performTextInput("Anything")

        composeRule
            .onNodeWithContentDescription("Search...")
            .assertIsDisplayed()
        composeRule
            .onNodeWithContentDescription("Search...")
            .performClick()

        composeRule
            .onNodeWithText("Carbs")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Amount")
            .performTextInput(addedAmount.toString())

        composeRule
            .onNodeWithContentDescription("Track")
            .performClick()

        assertThat(navController
            .currentDestination
            ?.route
            ?.startsWith(Route.TRACKER_OVERVIEW)).isTrue()

        composeRule
            .onAllNodesWithText(expectedCalories.toStr())
            .onFirst()
            .assertIsDisplayed()

        composeRule
            .onAllNodesWithText(expectedCarbs.toStr())
            .onFirst()
            .assertIsDisplayed()

        composeRule
            .onAllNodesWithText(expectedProtein.toStr())
            .onFirst()
            .assertIsDisplayed()

        composeRule
            .onAllNodesWithText(expectedFat.toStr())
            .onFirst()
            .assertIsDisplayed()

    }
}