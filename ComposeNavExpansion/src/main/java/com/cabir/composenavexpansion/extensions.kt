package com.cabir.composenavexpansion

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
import androidx.navigation.navOptions
import com.cabir.composenavexpansion.controller.LocalNavHostController


inline fun <reified T : Fragment> NavGraphBuilder.fragment(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: (NavBackStackEntry) -> T
) {
    val fragmentContent: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = {
        val navHostController = LocalNavHostController.current
        val fragment = remember {
            navHostController.manage(it.destination.route) { content(it) }
        }
        ComposeFragmentContainer(fragment = fragment, backStackEntry = it)
    }

    addDestination(
        ComposeNavigator.Destination(
            provider[ComposeNavigator::class],
            fragmentContent
        ).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}

@MainThread
@JvmOverloads
fun NavController.navigate(
    route: String,
    args: Bundle? = bundleOf(),
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions(builder), null)
    } else {
        navigate(route, navOptions(builder), null)
    }
}