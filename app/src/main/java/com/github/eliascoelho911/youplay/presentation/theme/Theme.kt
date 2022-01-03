package com.github.eliascoelho911.youplay.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val colors = lightColors(
    primary = WhiteF3F2FF,
    primaryVariant = WhiteF3F2FF,
    secondary = RedEC5462,
    secondaryVariant = RedEC5462,
    background = Blue031F3F,
    surface = WhiteF3F2FF,
    onPrimary = Blue031F3F,
    onSecondary = WhiteF3F2FF,
    onBackground = WhiteF3F2FF,
    onSurface = Blue031F3F
)

@Composable
fun YouPlayTheme(
    content: @Composable() () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}