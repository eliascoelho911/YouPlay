package com.github.eliascoelho911.youplay.presentation.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

@Composable
fun ShapeProgressIndicator(modifier: Modifier = Modifier, shape: Shape = RectangleShape) {
    val glowHeightMultiplier = 0.2f

    val transition = rememberInfiniteTransition()
    val glowYMultiplier: Float by transition.animateFloat(initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1000)
        )
    )
    val glowAlpha: Float by transition.animateFloat(initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(1000)
        )
    )

    Surface(modifier = modifier, color = Color.White.copy(alpha = 0.1f), shape = shape) {
        Canvas(modifier, onDraw = {
            drawRect(Color.White.copy(alpha = 0.1f),
                topLeft = Offset(0f, size.height * glowYMultiplier),
                size = Size(size.width, size.height * glowHeightMultiplier), glowAlpha)
        })
    }
}