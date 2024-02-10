package online.nsandroid.smartbmicalculator.navigation.destination

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import online.nsandroid.smartbmicalculator.navigation.Screen
import online.nsandroid.smartbmicalculator.ui.compose.BMIResultContainer
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel

fun NavGraphBuilder.bmiResultGraphDestination(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: BmiCalculatorViewModel
) {
    composable(Screen.BMIResultScreen.route) {
        BMIResultContainer(viewModel = viewModel)
    }
}