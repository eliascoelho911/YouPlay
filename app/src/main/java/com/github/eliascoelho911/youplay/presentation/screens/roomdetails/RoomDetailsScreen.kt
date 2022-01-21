package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.global.on
import com.github.eliascoelho911.youplay.domain.entities.Album
import com.github.eliascoelho911.youplay.domain.entities.Artist
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.presentation.common.AnimationDurations.medium
import com.github.eliascoelho911.youplay.presentation.common.AppTopBarWithCentralizedTitle
import com.github.eliascoelho911.youplay.presentation.common.screenPadding
import com.github.eliascoelho911.youplay.presentation.theme.YouPlayTheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun RoomDetailsScreen(
    viewModel: RoomDetailsViewModel,
    backgroundColor: Color,
    onConfirmExitFromRoom: () -> Unit,
    onClickOptions: () -> Unit,
    onUpdateRoomName: (String) -> Unit,
    onClickSkipToPreviousMusicButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
    onClickPlayOrPauseButton: () -> Unit,
    onClickShuffleButton: () -> Unit,
    onClickRepeatButton: () -> Unit,
    onTimeChange: (Int) -> Unit,
) {
    val roomResource by viewModel.currentRoom.collectAsState(initial = Resource.loading())
    val currentMusicResource by viewModel.currentMusic.collectAsState(initial = Resource.loading())
    var showExitFromRoomDialog by remember { mutableStateOf(false) }
    var showLoadingAction by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Background(color = backgroundColor)
        roomResource.on(success = { room ->
            Scaffold(topBar = {
                RoomDetailsTopBar(
                    roomName = room.name,
                    onUpdateTitle = onUpdateRoomName,
                    onClickExitFromRoom = {
                        showExitFromRoomDialog = true
                    },
                    onClickOptions = onClickOptions)
            }) {
                RoomDetailsContent(
                    room = room,
                    currentMusicResource = currentMusicResource,
                    onClickSkipToPreviousMusicButton = onClickSkipToPreviousMusicButton,
                    onClickSkipToNextMusicButton = onClickSkipToNextMusicButton,
                    onClickPlayOrPauseButton = onClickPlayOrPauseButton,
                    onTimeChange = onTimeChange,
                    onClickShuffleButton = onClickShuffleButton,
                    onClickRepeatButton = onClickRepeatButton
                )
            }
        })
    }

    if (showExitFromRoomDialog)
        ExitFromRoomDialog(onConfirmExitFromRoom = {
            showExitFromRoomDialog = false
            showLoadingAction = true
            onConfirmExitFromRoom()
        }, onDismissExitRoom = {
            showExitFromRoomDialog = false
        })

    if (showLoadingAction)
        LoadingAction()
}


@Composable
private fun LoadingAction() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Background(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawRect(Color.Black.copy(alpha = 0.15f).compositeOver(color), size = size)
        drawRect(Brush.linearGradient(
            0.5f to Color.Transparent,
            1f to Color.Black.copy(alpha = 0.84f),
            start = Offset.Zero,
            end = Offset(x = 0f, y = size.height)
        ), size = size)
    })
}

@Composable
private fun RoomDetailsContent(
    room: Room,
    currentMusicResource: Resource<Music>,
    onClickSkipToPreviousMusicButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
    onClickPlayOrPauseButton: () -> Unit,
    onTimeChange: (Int) -> Unit,
    onClickShuffleButton: () -> Unit,
    onClickRepeatButton: () -> Unit,
) {
    Column(modifier = Modifier
        .screenPadding(horizontal = false)
        .navigationBarsPadding()
        .fillMaxSize()) {
        //TODO demais casos
        currentMusicResource.on(success = { currentMusic ->
            currentMusic.album.imageUrl?.let { url ->
                AlbumImage(modifier = Modifier.screenPadding(vertical = false),
                    imageUrl = url)
            }
            Player(
                currentMusic = currentMusic,
                player = room.player,
                onTimeChange = onTimeChange,
                onClickShuffleButton = onClickShuffleButton,
                onClickSkipToPreviousMusicButton = onClickSkipToPreviousMusicButton,
                onClickPlayOrPauseButton = onClickPlayOrPauseButton,
                onClickSkipToNextMusicButton = onClickSkipToNextMusicButton,
                onClickRepeatButton = onClickRepeatButton
            )
        })
    }
}

@Composable
private fun RoomDetailsTopBar(
    roomName: String,
    onUpdateTitle: (String) -> Unit,
    onClickExitFromRoom: () -> Unit,
    onClickOptions: () -> Unit,
) {
    var title by remember { mutableStateOf(roomName) }
    title = roomName
    AppTopBarWithCentralizedTitle(modifier = Modifier.statusBarsPadding(),
        iconBack = Icons.Rounded.Close,
        title = {
            RoomTitle(title = title, onValueChange = { newTitle ->
                title = newTitle
            }, onUpdateTitle = onUpdateTitle)
        },
        onBackPressed = onClickExitFromRoom,
        actions = {
            OptionsButton(onClickOptions)
        })
}


@Composable
private fun RoomTitle(
    title: String,
    onValueChange: (String) -> Unit,
    onUpdateTitle: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(value = title,
        onValueChange = onValueChange,
        maxLines = 1,
        cursorBrush = SolidColor(colors.surface),
        singleLine = true,
        textStyle = typography.h6.copy(color = colors.surface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onUpdateTitle(title)
            focusManager.clearFocus(force = true)
        }))
}

@Composable
private fun OptionsButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Rounded.MoreVert,
            contentDescription = stringResource(id = R.string.options),
            tint = colors.onBackground)
    }
}

@Composable
private fun ColumnScope.AlbumImage(modifier: Modifier = Modifier, imageUrl: String?) {
    var targetImageAlpha by remember { mutableStateOf(0f) }
    val imageAlpha by animateFloatAsState(targetValue = targetImageAlpha,
        animationSpec = tween(if (targetImageAlpha == 0f) 0 else medium))
    Image(modifier = modifier
        .clip(CircleShape)
        .size(AlbumImageSize)
        .alpha(imageAlpha)
        .align(Alignment.CenterHorizontally)
        .shadow(AlbumImageElevation, shape = CircleShape),
        painter = rememberImagePainter(data = imageUrl,
            builder = {
                listener(onSuccess = { _, _ ->
                    targetImageAlpha = 1f
                }, onStart = {
                    targetImageAlpha = 0f
                })
            }), contentDescription = stringResource(id = R.string.roomDetails_albumImage))

}

@Composable
private fun ExitFromRoomDialog(onConfirmExitFromRoom: () -> Unit, onDismissExitRoom: () -> Unit) {
    AlertDialog(onDismissRequest = {
        onDismissExitRoom()
    }, dismissButton = {
        DialogButton(onClick = {
            onDismissExitRoom()
        }, text = stringResource(id = R.string.roomDetails_notExitRoom))
    }, confirmButton = {
        DialogButton(onClick = onConfirmExitFromRoom,
            text = stringResource(id = R.string.roomDetails_confirmExitRoom))
    }, title = {
        Text(stringResource(id = R.string.roomDetails_exitRoomDialogTitle),
            color = colors.onSurface,
            style = typography.h6)
    })
}

@Composable
private fun DialogButton(
    onClick: () -> Unit,
    text: String,
) {
    Text(modifier = Modifier
        .clickable(onClick = onClick)
        .padding(DialogButtonPadding),
        text = text.uppercase(),
        color = colors.secondary, style = typography.button)
}

@Composable
@Preview(showBackground = true)
private fun RoomDetailsContentPreview() {
    YouPlayTheme {
        RoomDetailsContent(
            room = Room(name = stringResource(id = R.string.defaultRoomName, "Elias"),
                id = "",
                player = PlayerData(),
                currentMusicId = "",
                ownerId = ""),
            currentMusicResource = Resource.success(Music(id = "",
                name = "Sonhei que tava me casando",
                artists = listOf(Artist("WS", "Wesley Safadão")),
                album = Album(name = "Album", imageUrl = "", id = ""),
                durationInSeconds = 300)),
            onClickSkipToPreviousMusicButton = {},
            onClickSkipToNextMusicButton = {},
            onClickPlayOrPauseButton = {},
            onTimeChange = {},
            onClickShuffleButton = {},
            onClickRepeatButton = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ExitFromRoomDialogPreview() {
    YouPlayTheme {
        ExitFromRoomDialog(
            onConfirmExitFromRoom = {},
            onDismissExitRoom = {},
        )
    }
}

private val DialogButtonPadding = 8.dp
private val AlbumImageSize = 250.dp
private val AlbumImageElevation = 16.dp