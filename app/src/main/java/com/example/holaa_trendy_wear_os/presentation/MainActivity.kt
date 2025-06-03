package com.example.holaa_trendy_wear_os.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.holaa_trendy_wear_os.presentation.home.HomeScreen
import com.example.holaa_trendy_wear_os.presentation.flash_deals_screen.FlashDealsScreen
import com.example.holaa_trendy_wear_os.presentation.theme.HolaaTrendyWearOsTheme
import com.example.holaa_trendy_wear_os.presentation.tranding.TrendingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaaTrendyWearOsTheme {
                TrendyApp()
            }
        }
    }
}

@Composable
fun TrendyApp() {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToFlashDeals = {
                    navController.navigate("flash_deals")
                },
                onNavigateToTrending = {
                    navController.navigate("trending")
                }
            )
        }

        composable("flash_deals") {
            FlashDealsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("trending") {
            TrendingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}