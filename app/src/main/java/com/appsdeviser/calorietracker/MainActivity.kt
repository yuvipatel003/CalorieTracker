package com.appsdeviser.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsdeviser.calorietracker.navigation.navigate
import com.appsdeviser.calorietracker.ui.theme.CalorieTrackerTheme
import com.appsdeviser.core.domain.model.ActivityLevel
import com.appsdeviser.core.navigation.Route
import com.appsdeviser.onboarding_presentation.screens.activity_level.ActivityLevelScreen
import com.appsdeviser.onboarding_presentation.screens.age.AgeScreen
import com.appsdeviser.onboarding_presentation.screens.gender.GenderScreen
import com.appsdeviser.onboarding_presentation.screens.goal.GoalTypeScreen
import com.appsdeviser.onboarding_presentation.screens.height.HeightScreen
import com.appsdeviser.onboarding_presentation.screens.nutrient_goal.NutrientGoalScreen
import com.appsdeviser.onboarding_presentation.screens.weight.WeightScreen
import com.appsdeviser.onboarding_presentation.screens.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        startDestination = Route.WELCOME,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Route.WELCOME){
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE){
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate)
                        }
                        composable(Route.GENDER){
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.HEIGHT){
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate)
                        }
                        composable(Route.WEIGHT){
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate)
                        }
                        composable(Route.NUTRIENT_GOAL){
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY){
                            ActivityLevelScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GOAL){
                            GoalTypeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.TRACKER_OVERVIEW){

                        }
                        composable(Route.SEARCH){

                        }
                    }
                }
            }
        }
    }
}