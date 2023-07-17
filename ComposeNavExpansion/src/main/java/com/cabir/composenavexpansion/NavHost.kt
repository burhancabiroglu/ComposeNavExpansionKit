package com.cabir.composenavexpansion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
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



val LocalNavHostController = compositionLocalOf <NavHostController> { error("LocalNavHostController does not provided") }
