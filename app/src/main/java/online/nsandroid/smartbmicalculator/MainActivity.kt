package online.nsandroid.smartbmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import online.nsandroid.smartbmicalculator.navigation.NavigationContainer
import online.nsandroid.smartbmicalculator.ui.theme.SmartBMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartBMICalculatorTheme {
                NavigationContainer()
            }
        }
    }
}
