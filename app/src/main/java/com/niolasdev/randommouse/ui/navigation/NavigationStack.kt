package com.niolasdev.randommouse.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.niolasdev.randommouse.CatHome
import com.niolasdev.randommouse.CatsViewModel
import com.niolasdev.randommouse.ui.CatDetailViewModel
import com.niolasdev.randommouse.ui.widget.CatDetailScreen


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavigationStack(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home) {
        composable<Screen.Home> {
            CatHome(
                viewModel = hiltViewModel<CatsViewModel>(),
                navController = navController,
                modifier = modifier
            )
        }
        composable<Screen.Details> { backStackEntry ->

            val details = backStackEntry.toRoute<Screen.Details>()

            CatDetailScreen(
                catId = details.catId,
                navController = navController,
                modifier = modifier,
                viewModel = hiltViewModel<CatDetailViewModel>()
            )
        }
    }
}