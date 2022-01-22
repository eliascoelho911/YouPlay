package com.github.eliascoelho911.youplay.presentation.ui.base.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CodeTextField(
    modifier: Modifier,
    codeLength: Int = 5,
    whenFull: (smsCode: String) -> Unit,
    whenNotFull: () -> Unit,
) {
    val enteredCode = remember {
        mutableStateListOf(
            *((0 until codeLength).map { "" }.toTypedArray())
        )
    }
    val focusRequesters: List<FocusRequester> = remember {
        (0 until codeLength).map { FocusRequester() }
    }
    Row(modifier) {
        (0 until codeLength).forEach { index ->
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .onKeyEvent { event ->
                        val cellValue = enteredCode[index]
                        if (event.type == KeyEventType.KeyUp) {
                            if (event.key == Key.Backspace && cellValue == "") {
                                focusRequesters
                                    .getOrNull(index - 1)
                                    ?.requestFocus()
                                enteredCode[index - 1] = ""
                            } else if (cellValue != "") {
                                focusRequesters
                                    .getOrNull(index + 1)
                                    ?.requestFocus()
                            }
                        }
                        false
                    }
                    .focusOrder(focusRequesters[index])
                    .focusRequester(focusRequesters[index]),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colors.background,
                    unfocusedIndicatorColor = colors.onBackground,
                    focusedIndicatorColor = colors.onBackground,
                    cursorColor = colors.onBackground,
                    textColor = colors.onBackground
                ),
                textStyle = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                singleLine = true,
                value = enteredCode[index],
                onValueChange = {
                    val value = it.uppercase()
                    if (value.length > 1) {
                        enteredCode[index] = value.last().toString()
                        return@TextField
                    }
                    if (focusRequesters[index].freeFocus()) {
                        enteredCode[index] = value
                        if (enteredCode[index].isBlank() && index > 0 && index < 5) {
                            focusRequesters[index - 1].requestFocus()
                        } else if (index < codeLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (enteredCode.size == 5) {
                            whenFull(enteredCode.joinToString(separator = ""))
                        } else {
                            whenNotFull()
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}