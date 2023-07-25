package com.cabir.composenavexpansion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.cabir.composenavexpansion.controller.AppNavHostController
import com.cabir.composenavexpansion.controller.LocalNavHostController
import com.cabir.composenavexpansion.controller.rememberAppNavController


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: AppNavHostController = rememberAppNavController(),
    startDestination: String,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    CompositionLocalProvider(LocalNavHostController provides navController) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination,
            route = route,
            builder = builder
        )
    }
}