package com.github.eliascoelho911.youplay.presentation.util

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.setValueToOpposite() {
    value = !value
}