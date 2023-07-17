package com.cabir.composenavexpansion

import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager

/** Access to package-private method in FragmentManager through reflection */
fun FragmentManager.onContainerAvailable(view: FragmentContainerView) {
    val method = FragmentManager::class.java.getDeclaredMethod(
        "onContainerAvailable",
        FragmentContainerView::class.java
    )
    method.isAccessible = true
    method.invoke(this, view)
}
