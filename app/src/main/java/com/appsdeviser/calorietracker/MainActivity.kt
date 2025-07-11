package com.appsdeviser.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appsdeviser.calorietracker.ui.theme.CalorieTrackerTheme
import com.appsdeviser.core.domain.preferences.Preferences
import com.appsdeviser.calorietracker.navigation.Route
import com.appsdeviser.onboarding_presentation.screens.activity_level.ActivityLevelScreen
import com.appsdeviser.onboarding_presentation.screens.age.AgeScreen
import com.appsdeviser.onboarding_presentation.screens.gender.GenderScreen
import com.appsdeviser.onboarding_presentation.screens.goal.GoalTypeScreen
import com.appsdeviser.onboarding_presentation.screens.height.HeightScreen
import com.appsdeviser.onboarding_presentation.screens.nutrient_goal.NutrientGoalScreen
import com.appsdeviser.onboarding_presentation.screens.weight.WeightScreen
import com.appsdeviser.onboarding_presentation.screens.welcome.WelcomeScreen
import com.appsdeviser.tracker_presentation.search.SearchScreen
import com.appsdeviser.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding = preferences.loadShouldShowOnBoarding()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ){ padding ->
                    NavHost(
                        navController = navController,
                        startDestination = if(shouldShowOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Route.WELCOME){
                            WelcomeScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }
                        composable(Route.AGE){
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.GENDER)
                                })
                        }
                        composable(Route.GENDER){
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.HEIGHT)
                            })
                        }
                        composable(Route.HEIGHT){
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                })
                        }
                        composable(Route.WEIGHT){
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.NUTRIENT_GOAL)
                                })
                        }
                        composable(Route.NUTRIENT_GOAL){
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                }
                            )
                        }
                        composable(Route.ACTIVITY){
                            ActivityLevelScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }
                        composable(Route.GOAL){
                            GoalTypeScreen(onNextClick = {
                                navController.navigate(Route.TRACKER_OVERVIEW)
                            })
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, dayOfMonth, month, year ->
                                navController.navigate(
                                    Route.SEARCH
                                            + "/${mealName}"
                                            + "/${dayOfMonth}"
                                            + "/${month}"
                                            + "/${year}"
                                )
                            })
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName"){
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth"){
                                    type = NavType.IntType
                                },
                                navArgument("month"){
                                    type = NavType.IntType
                                },
                                navArgument("year"){
                                    type = NavType.IntType
                                },
                            )
                        ){
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
                            )
                        }
                    }
                }
            }
        }
    }
}