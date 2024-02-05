package online.nsandroid.smartbmicalculator.navigation.destination

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import online.nsandroid.smartbmicalculator.navigation.Screen
import online.nsandroid.smartbmicalculator.ui.compose.WelcomeContainer

fun NavGraphBuilder.welcomeGraphDestination(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    composable(Screen.WelcomeScreen.route) {
        WelcomeContainer(
            onClick = {
                navHostController.navigate(Screen.BMICalculatorScreen.route)
            }
        )
    }
}