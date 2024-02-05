package online.nsandroid.smartbmicalculator.navigation

sealed class Screen(val route: String) {
    data object WelcomeScreen : Screen("WelcomeScreen")
    data object BMICalculatorScreen : Screen("BMICalculatorScreen")
    data object BMIResultScreen : Screen("BMIResultScreen")
}