package com.github.eliascoelho911.youplay.presentation.ui.base.components

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.github.eliascoelho911.youplay.presentation.navigation.Destination

fun NavHostController.navigate(
    destination: Destination,
    arguments: Map<String, Any> = emptyMap(),
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(route = destination.route(arguments), builder = builder)
}