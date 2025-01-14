package com.github.eliascoelho911.youplay.presentation.ui.screens.accessroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.presentation.ui.base.components.AppTopBarWithCentralizedTitle
import com.github.eliascoelho911.youplay.presentation.ui.base.components.ButtonWithLoading
import com.github.eliascoelho911.youplay.presentation.ui.base.components.CodeTextField
import com.github.eliascoelho911.youplay.presentation.ui.base.components.screenPadding
import com.github.eliascoelho911.youplay.presentation.ui.theme.YouPlayTheme
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

//todo: refatorar para novo padrão

@Composable
fun AccessRoomScreen(
    roomAccessIsLoading: Boolean,
    onBackPressed: () -> Unit,
    onClickAccessWithCodeButton: (String) -> Unit,
    onClickAccessWithQrCode: () -> Unit,
) {
    Scaffold(topBar = { AccessRoomTopBar(onBackPressed) }) {
        AccessRoomContent(roomAccessIsLoading,
            onClickAccessWithCodeButton,
            onClickAccessWithQrCode)
    }
}

@Composable
private fun AccessRoomTopBar(onBackPressed: () -> Unit) {
    AppTopBarWithCentralizedTitle(modifier = Modifier.statusBarsPadding(), title = {
        Text(text = stringResource(id = R.string.accessRoom_screenTitle),
            style = typography.h6, color = colors.onBackground)
    }, onBackPressed = onBackPressed)
}

@Composable
fun AccessRoomContent(
    roomAccessIsLoading: Boolean,
    onClickAccessWithCodeButton: (String) -> Unit,
    onClickAccessWithQrCode: () -> Unit,
) {
    Column(Modifier
        .background(colors.background)
        .fillMaxSize()
        .navigationBarsPadding()) {
        Column(modifier = Modifier
            .screenPadding()
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccessWithCode(roomAccessIsLoading, onClickAccessWithCodeButton)
            Spacer(modifier = Modifier.height(OrTextMargin))
            Text(text = stringResource(id = R.string.accessRoom_or),
                style = typography.body2,
                color = Color.White.copy(alpha = 0.6f))
            Spacer(modifier = Modifier.height(AccessWithQrCodeMargin))
            AccessWithQrCode(onClickAccessWithQrCode)
        }
    }
}

@Composable
fun AccessWithQrCode(onClickAccessWithQrCode: () -> Unit) {
    TextButton(onClick = onClickAccessWithQrCode) {
        Text(text = stringResource(id = R.string.accessRoom_qrcode),
            style = typography.body1,
            color = colors.onBackground,
            fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.width(AccessWithQrCodeTextMargin))
        Icon(painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = stringResource(id = R.string.accessRoom_qrcodeDescription),
            tint = colors.onBackground
        )
    }
}

@Composable
private fun AccessWithCode(
    roomAccessIsLoading: Boolean,
    onClickAccessWithCodeButton: (String) -> Unit,
) {
    Text(text = stringResource(id = R.string.accessRoom_labelCode),
        style = typography.body2,
        color = Color.White.copy(alpha = 0.6f))
    Spacer(Modifier.height(AccessWithCodeTextFieldMargin))
    ConstraintLayout {
        val (codeTextFieldRef, buttonRef) = createRefs()
        var code by remember { mutableStateOf("") }

        CodeTextField(
            modifier = Modifier.constrainAs(codeTextFieldRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            codeLength = RoomIDGenerator.LengthRoomUUID,
            whenFull = {
                code = it
            },
            whenNotFull = {
                code = ""
            }
        )

        ButtonWithLoading(
            modifier = Modifier.constrainAs(buttonRef) {
                top.linkTo(codeTextFieldRef.bottom, AccessWithCodeButtonMargin)
                start.linkTo(codeTextFieldRef.start)
                end.linkTo(codeTextFieldRef.end)

                width = Dimension.fillToConstraints
            },
            buttonModifier = Modifier.fillMaxWidth(),
            loading = roomAccessIsLoading,
            buttonIsEnabled = code.length == RoomIDGenerator.LengthRoomUUID,
            onClick = { onClickAccessWithCodeButton(code) },
            buttonContent = {
                Text(text = stringResource(id = R.string.accessRoom_withCodeButton).uppercase(),
                    color = colors.background, style = typography.button)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    YouPlayTheme {
        AccessRoomContent(roomAccessIsLoading = false,
            onClickAccessWithCodeButton = {},
            onClickAccessWithQrCode = {})
    }
}

private val AccessWithCodeTextFieldMargin = 8.dp
private val AccessWithCodeButtonMargin = 16.dp
private val OrTextMargin = 8.dp
private val AccessWithQrCodeMargin = 8.dp
private val AccessWithQrCodeTextMargin = 8.dp