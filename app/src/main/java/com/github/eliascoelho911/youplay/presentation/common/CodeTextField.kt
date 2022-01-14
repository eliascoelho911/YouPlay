package com.github.eliascoelho911.youplay.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    code: Map<Int, String>,
    onValueChange: (position: Int, String) -> Unit,
    amountOfCodeBoxes: Int,
) {
    val focusRequesters = List(amountOfCodeBoxes) { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Row(modifier) {
        for (position in 0 until amountOfCodeBoxes) {
            if (position in 1 until amountOfCodeBoxes)
                Spacer(Modifier.width(CodeBoxItemMargin))

            val currentFocusRequester = focusRequesters[position]
            val nextFocusRequester = focusRequesters.getOrNull(position + 1)

            CodeBoxItem(focusRequester = currentFocusRequester,
                nextFocusRequester = nextFocusRequester,
                focusManager = focusManager,
                value = code.getOrDefault(position, ""),
                onValueChange = { newValue ->
                    val maxLength = 1
                    if (newValue.length > maxLength) {
                        if (currentFocusRequester.captureFocus()) {
                            val oldValue = code[position]
                            val lastValueEntered = keepOnlyLastEnteredValue(oldValue, newValue)
                            onValueChange(position, lastValueEntered.uppercase())
                            currentFocusRequester.freeFocus()
                        }
                    } else {
                        onValueChange(position, newValue.uppercase())
                    }

                    val isLast = position == amountOfCodeBoxes - 1
                    if (isLast)
                        focusManager.clearFocus(force = true)
                    else if (newValue.isNotEmpty())
                        nextFocusRequester?.requestFocus()
                }
            )
        }
    }
}

@Composable
private fun CodeBoxItem(
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    focusManager: FocusManager,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Box(Modifier
        .size(CodeBoxSize)
        .border(CodeBoxBorderWidth, Color.White.copy(alpha = 0.6f), MaterialTheme.shapes.small)
        .clip(MaterialTheme.shapes.small)
        .focusOrder(focusRequester) {
            nextFocusRequester?.requestFocus()
        }) {
        val keyboardOptions = if (nextFocusRequester == null)
            KeyboardOptions(imeAction = ImeAction.Done)
        else
            KeyboardOptions(imeAction = ImeAction.Next)

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.h5.copy(color = colors.onBackground,
                textAlign = TextAlign.Center),
            cursorBrush = SolidColor(colors.onBackground),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
            }))
    }
}

private fun keepOnlyLastEnteredValue(
    oldValue: String?,
    newValue: String,
): String {
    val lastValueEntered = oldValue?.let { currentValue ->
        newValue.replaceFirst(currentValue, "")
    } ?: newValue

    return if (lastValueEntered.length > 1)
        lastValueEntered.last().toString()
    else
        lastValueEntered
}

private val CodeBoxSize = 48.dp
private val CodeBoxBorderWidth = 1.dp
private val CodeBoxItemMargin = 8.dp