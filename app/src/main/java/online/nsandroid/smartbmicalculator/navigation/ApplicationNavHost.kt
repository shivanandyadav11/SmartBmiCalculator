package online.nsandroid.smartbmicalculator.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import online.nsandroid.smartbmicalculator.navigation.destination.bmiCalculatorGraphDestination
import online.nsandroid.smartbmicalculator.navigation.destination.bmiResultGraphDestination
import online.nsandroid.smartbmicalculator.navigation.destination.welcomeGraphDestination

@Composable
fun ApplicationNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.route,
    ) {
        welcomeGraphDestination(
            navHostController = navController,
            paddingValues = paddingValues
        )
        bmiCalculatorGraphDestination(
            navHostController = navController,
            paddingValues = paddingValues
        )
        bmiResultGraphDestination(
            navHostController = navController,
            paddingValues = paddingValues
        )
    }
}