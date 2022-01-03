package com.github.eliascoelho911.youplay.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.eliascoelho911.youplay.R

private val ubuntuFontFamily = FontFamily(
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_regular, FontWeight.Normal),
    Font(R.font.ubuntu_medium, FontWeight.Medium),
    Font(R.font.ubuntu_bold, FontWeight.Bold)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 98.sp,
        letterSpacing = (-1.5).sp,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 61.sp,
        letterSpacing = (-0.5).sp,
        color = Color.White
    ),
    h3 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 49.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ),
    h4 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
        letterSpacing = 0.25.sp,
        color = Color.White
    ),
    h5 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ),
    h6 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp,
        color = Color.White
    ),
    subtitle1 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        color = Color.White
    ),
    subtitle2 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        color = Color.White
    ),
    body1 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),
    body2 = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        color = Color.White
    ),
    button = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
        color = Color.White,
    ),
    caption = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = Color.White
    ),
    overline = TextStyle(
        fontFamily = ubuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        color = Color.White
    )
)