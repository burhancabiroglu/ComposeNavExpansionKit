package com.cabir.composenavexpansion

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.cabir.composenavexpansion.controller.AppNavHostController
import com.cabir.composenavexpansion.controller.LocalNavHostController
import com.cabir.composenavexpansion.controller.rememberAppNavController


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: AppNavHostController = rememberAppNavController(),
    startDestination: String,
    route: String? = null,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        exitTransition,
    builder: NavGraphBuilder.() -> Unit
) {
    CompositionLocalProvider(LocalNavHostController provides navController) {
        androidx.navigation.compose.NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination,
            route = route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            builder = builder
        )
    }
}