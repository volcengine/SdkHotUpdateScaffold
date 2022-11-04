package com.volcengine.zeusscaffold.navigation

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

fun NavController.safeNavigate(
    activity: FragmentActivity,
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null
) {
    // 尽量在每次可能加载插件fragment时，替换一下Factory，防止被别人二次替换
    Utils.ensureFragmentFactory(activity)
    val node = currentDestination?.getAction(actionId)
    if (node == null) {
        Log.w("NavExtension", "cannot found destId $actionId in graph")
        return
    }
    navigate(actionId, args = args, navOptions = navOptions)
}

fun NavController.deepLinkNavigateTo(
    deepLinkDestination: String,
    popUpTo: Boolean = false,
    isSingleTop: Boolean = false
) {
    val builder = NavOptions.Builder().setLaunchSingleTop(isSingleTop)
    if (popUpTo) {
        builder.setPopUpTo(graph.startDestinationId, true)
    }

    try {
        navigate(
            buildDeepLink(deepLinkDestination),
            builder.build()
        )
    } catch (e: Exception) {
        e.printStackTrace()
        Log.w("NavExtension", "cannot navigate to $deepLinkDestination")
    }
}

fun buildDeepLink(destination: String) =
    NavDeepLinkRequest.Builder
        .fromUri(destination.toUri())
        .build()