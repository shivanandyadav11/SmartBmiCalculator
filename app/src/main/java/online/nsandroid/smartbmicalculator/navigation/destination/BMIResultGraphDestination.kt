package online.nsandroid.smartbmicalculator.navigation.destination

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import online.nsandroid.smartbmicalculator.model.ImageShareStatus
import online.nsandroid.smartbmicalculator.navigation.Screen
import online.nsandroid.smartbmicalculator.ui.compose.BMIResultContainer
import online.nsandroid.smartbmicalculator.ui.util.getBMIResultImage
import online.nsandroid.smartbmicalculator.ui.util.shareImage
import online.nsandroid.smartbmicalculator.viewModel.BmiCalculatorViewModel

fun NavGraphBuilder.bmiResultGraphDestination(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: BmiCalculatorViewModel
) {
    composable(Screen.BMIResultScreen.route) {
        val view: View = LocalView.current
        val context = LocalContext.current

        BMIResultContainer(viewModel = viewModel, shareYourBMI = {
            shareYourBMI(it, view, context )
        })

    }
}

fun shareYourBMI(rect: Rect, view: View, context: Context) {
    getBMIResultImage(rect, view, context) {
        when (it) {
            is ImageShareStatus.Success -> {
                shareImage(context, it.bitmap)
            }

            else -> {

            }
        }
    }
}

