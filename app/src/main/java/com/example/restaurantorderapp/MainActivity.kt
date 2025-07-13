package com.example.restaurantorderapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantorderapp.ui.theme.RestaurantOrderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantOrderAppTheme {
                //setup navigation
                val navController = rememberNavController()

                //determine if user logged in
                val sharedPreferences = getSharedPreferences("AppData", Context.MODE_PRIVATE)
                val firstName = sharedPreferences.getString("firstName", null)
                val lastName = sharedPreferences.getString("lastName", null)
                val email = sharedPreferences.getString("email", null)

                val startDestination = if (firstName != null && lastName != null && email != null) "home" else "profile"

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("home") {
                        HomeScreen(navController)
                    }
                    composable("profile") {
                        OnboardingScreen(navController)
                    }
                }
            }
        }
    }
}

