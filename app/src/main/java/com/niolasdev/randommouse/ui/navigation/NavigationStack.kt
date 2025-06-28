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
import com.niolasdev.randommouse.ui.widget.CatDetailCard


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
        composable<Screen.Details> {

            val cat = hiltViewModel<CatsViewModel>().selectedCat.value

            cat?.let { detailCat ->
                CatDetailCard(
                    cat = detailCat,
                    modifier = modifier,
                    navController = navController,
                )
            }

            /*val detail: Screen.Details = it.toRoute()
            CatDetailCard(
                cat = detail.cat,
                modifier = modifier,
                navController = navController,
            )*/
        }
    }
}