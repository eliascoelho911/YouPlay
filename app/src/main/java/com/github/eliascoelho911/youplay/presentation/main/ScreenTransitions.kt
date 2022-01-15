package com.github.eliascoelho911.youplay.presentation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.github.eliascoelho911.youplay.presentation.common.AnimationDurations

fun slideInHorizontallyTransition() =
    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(AnimationDurations.medium))

fun slideOutHorizontallyTransition() =
    slideOutHorizontally(targetOffsetX = { -1000 },
        animationSpec = tween(AnimationDurations.medium))