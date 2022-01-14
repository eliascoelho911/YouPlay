package com.github.eliascoelho911.youplay.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ButtonWithLoading(
    modifier: Modifier = Modifier,
    loading: Boolean,
    onClick: () -> Unit,
    buttonContent: @Composable () -> Unit,
) {
    Box(modifier) {
        Row(modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            AnimatedVisibility(visible = loading,
                enter = fadeIn(),
                exit = fadeOut()) {
                CircularProgressIndicator(modifier = Modifier
                    .size(
                        width = ButtonDefaults.MinHeight,
                        height = ButtonDefaults.MinHeight
                    ))
            }
        }
        Row(modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            AnimatedVisibility(visible = !loading,
                enter = fadeIn(),
                exit = fadeOut()) {
                Button(onClick = onClick) {
                    buttonContent()
                }
            }
        }
    }
}