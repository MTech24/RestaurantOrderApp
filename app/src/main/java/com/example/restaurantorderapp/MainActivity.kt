package com.example.restaurantorderapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantorderapp.ui.theme.RestaurantOrderAppTheme
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private val client = HttpClient(CIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                try {
                    val response = client.get("https://mtech24.cz/restaurant-app-api/menu/").bodyAsText()
                    Log.d("API_RESPONSE", response)
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Failed to fetch data: ${e.message}", e)
                }
            }

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

