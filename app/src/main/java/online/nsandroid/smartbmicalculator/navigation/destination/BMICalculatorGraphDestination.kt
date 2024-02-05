package online.nsandroid.smartbmicalculator.navigation.destination

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import online.nsandroid.smartbmicalculator.navigation.Screen
import online.nsandroid.smartbmicalculator.ui.compose.BMICalculatorContainer

fun NavGraphBuilder.bmiCalculatorGraphDestination(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    composable(Screen.BMICalculatorScreen.route) {
        BMICalculatorContainer()
    }
}