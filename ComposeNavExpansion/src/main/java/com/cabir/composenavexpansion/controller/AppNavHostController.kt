package com.cabir.composenavexpansion.controller

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator

class AppNavHostController(context: Context ): NavHostController(context) {
    private val iFragmentManager = IFragmentManager()

    fun manage(dest: String?, fragmentFactory: ()-> Fragment): Fragment {
        iFragmentManager.set(currentBackStack.value)
        return iFragmentManager.manage(dest.toString(),fragmentFactory)
    }
}

@Composable
public fun rememberAppNavController(vararg navigators: Navigator<out NavDestination>): AppNavHostController {
    val context = LocalContext.current
    return rememberSaveable(inputs = navigators, saver = appNavControllerSaver(context)) {
        createAppNavController(context)
    }.apply {
        for (navigator in navigators) {
            navigatorProvider.addNavigator(navigator)
        }
    }
}

private fun createAppNavController(context: Context) =
    AppNavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }

/**
 * Saver to save and restore the NavController across config change and process death.
 */
private fun appNavControllerSaver(
    context: Context
): Saver<AppNavHostController, *> = Saver<AppNavHostController, Bundle>(
    save = { it.saveState() },
    restore = { createAppNavController(context).apply { restoreState(it) } }
)

val LocalNavHostController = compositionLocalOf <AppNavHostController> { error("LocalNavHostController does not provided") }
