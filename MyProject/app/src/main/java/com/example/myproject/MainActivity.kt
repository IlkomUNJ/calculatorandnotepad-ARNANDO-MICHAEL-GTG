package com.example.myproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// Import Navigation yang memerlukan dependensi Gradle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myproject.ui.theme.MyProjectTheme
// Import the wrapper composables from their new packages
import com.example.myproject.calculator.CalculatorApp
// Import the correct NotepadScreen
import com.example.myproject.notepad.NotepadScreen

// Define screen routes
object Destinations {
    const val HOME = "home"
    const val CALCULATOR = "calculator"
    const val NOTEPAD = "notepad"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProjectTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME,
        modifier = Modifier.fillMaxSize()
    ) {
        // HOME SCREEN
        composable(Destinations.HOME) {
            HomeScreen(
                onNavigateToCalculator = { navController.navigate(Destinations.CALCULATOR) },
                onNavigateToNotepad = { navController.navigate(Destinations.NOTEPAD) }
            )
        }

        // CALCULATOR SCREEN
        composable(Destinations.CALCULATOR) {
            CalculatorApp(
                onBack = { navController.popBackStack() }
            )
        }

        // NOTEPAD SCREEN
        composable(Destinations.NOTEPAD) {
            NotepadScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}