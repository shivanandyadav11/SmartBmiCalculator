package online.nsandroid.smartbmicalculator.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import online.nsandroid.smartbmicalculator.navigation.destination.bmiCalculatorGraphDestination
import online.nsandroid.smartbmicalculator.navigation.destination.bmiResultGraphDestination
import online.nsandroid.smartbmicalculator.navigation.destination.welcomeGraphDestination
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel

@Composable
fun ApplicationNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    val viewModel = viewModel<BmiCalculatorViewModel>()
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
            paddingValues = paddingValues,
            viewModel = viewModel,
        )
        bmiResultGraphDestination(
            navHostController = navController,
            paddingValues = paddingValues,
            viewModel = viewModel,
        )
    }
}