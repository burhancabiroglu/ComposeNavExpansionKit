package com.cabir.composenavexpansion.controller

import androidx.fragment.app.Fragment
import androidx.navigation.NavBackStackEntry

class IFragmentManager {
    private val fragmentStack = ArrayList<Pair<String,Fragment>>()
    private val backStackHistory = ArrayList<List<NavBackStackEntry>>()

    fun set(backStackEntry:List<NavBackStackEntry>) {
        backStackHistory.add(backStackEntry)
    }

    fun manage(dest: String,fragmentFactory: () -> Fragment): Fragment {
        var navTrend = NavTrend.NONE
        val histSize = backStackHistory.size
        if (histSize >= 2) {
            val last  = backStackHistory.last()
            val prev = backStackHistory[histSize - 2]
            if(last.size > prev.size) {
                last.filterIndexed { index, navBackStackEntry ->
                    prev.getOrNull(index)?.id != navBackStackEntry.id
                }.also {
                    if(it.isNotEmpty()) navTrend = NavTrend.FORWARD
                }
            }
            else {
                prev.filterIndexed { index, navBackStackEntry ->
                    last.getOrNull(index)?.id != navBackStackEntry.id
                }.also {
                    if(it.isNotEmpty()) navTrend = NavTrend.BACKWARD
                }
            }
        }
        return when(navTrend){
            NavTrend.FORWARD -> {
                val fragment = fragmentFactory()
                fragmentStack.add(dest to fragment)
                fragment
            }
            NavTrend.BACKWARD -> {
                fragmentStack.removeLastOrNull()
                fragmentStack.lastOrNull()?.second?:Fragment()
            }
            NavTrend.NONE -> {
                val fragment = fragmentFactory()
                fragmentStack.add(dest to fragment)
                fragment
            }
        }
    }


    private enum class NavTrend {
        FORWARD,
        BACKWARD,
        NONE
    }
}