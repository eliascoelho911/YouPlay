package com.github.eliascoelho911.youplay.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.screenPadding(horizontal: Boolean = true, vertical: Boolean = true): Modifier =
    then(
        padding(horizontal = ScreenPadding.takeIf { horizontal } ?: 0.dp,
            vertical = ScreenPadding.takeIf { vertical } ?: 0.dp)
    )

fun Modifier.screenPadding(
    start: Boolean = false,
    end: Boolean = false,
    top: Boolean = false,
    bottom: Boolean = false,
): Modifier =
    then(
        padding(start = ScreenPadding.takeIf { start } ?: 0.dp,
            end = ScreenPadding.takeIf { end } ?: 0.dp,
            top = ScreenPadding.takeIf { top } ?: 0.dp,
            bottom = ScreenPadding.takeIf { bottom } ?: 0.dp)
    )

private val ScreenPadding = 16.dp