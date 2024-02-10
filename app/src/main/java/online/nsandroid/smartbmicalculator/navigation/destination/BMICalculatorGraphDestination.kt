package online.nsandroid.smartbmicalculator.navigation.destination

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import online.nsandroid.smartbmicalculator.model.UserData
import online.nsandroid.smartbmicalculator.navigation.Screen
import online.nsandroid.smartbmicalculator.ui.compose.BMICalculatorContainer
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel

fun NavGraphBuilder.bmiCalculatorGraphDestination(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: BmiCalculatorViewModel
) {
    composable(Screen.BMICalculatorScreen.route) {
        BMICalculatorContainer(onCalculateClick = { name, gender, age, height, weight ->
            run {
                viewModel.setUserData(
                    UserData(
                        name = name,
                        gender = gender,
                        age = age,
                        height = height * 0.01,
                        weight = weight
                    )
                )
                navHostController.navigate(route = Screen.BMIResultScreen.route)
            }
        }
        )
    }
}