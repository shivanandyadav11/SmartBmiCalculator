package online.nsandroid.smartbmicalculator.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun NavigationContainer(
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold { paddingValues ->
        ApplicationNavHost(navController = navController, paddingValues = paddingValues)
    }
}