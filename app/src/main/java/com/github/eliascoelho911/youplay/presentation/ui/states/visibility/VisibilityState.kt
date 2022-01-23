package com.github.eliascoelho911.youplay.presentation.ui.states.visibility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class VisibilityState(initialValue: Visibility) {
    var currentValue by mutableStateOf(initialValue)
        private set

    fun show() {
        currentValue = Visibility.Visible
    }

    fun hide() {
        currentValue = Visibility.Hide
    }

    fun isVisible() = currentValue == Visibility.Visible

    fun isHide() = currentValue == Visibility.Hide

    companion object {
        fun saver(): Saver<VisibilityState, *> = Saver(
            save = { it.currentValue },
            restore = { VisibilityState(initialValue = it) }
        )
    }
}

@Composable
fun rememberVisibilityState(initialValue: Visibility): VisibilityState =
    rememberSaveable(saver = VisibilityState.saver()) {
        VisibilityState(initialValue)
    }