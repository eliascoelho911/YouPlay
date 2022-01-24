package com.github.eliascoelho911.youplay.presentation.ui.base.screens.roomdetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomDrawer
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.presentation.common.AnimationDurations.medium
import com.github.eliascoelho911.youplay.presentation.ui.base.components.AppTopBarWithCentralizedTitle
import com.github.eliascoelho911.youplay.presentation.ui.base.components.screenPadding
import com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails.Player
import com.github.eliascoelho911.youplay.presentation.ui.states.roomdetails.RoomDetailsState
import com.github.eliascoelho911.youplay.presentation.ui.theme.Black0E0E0E
import com.github.eliascoelho911.youplay.presentation.ui.theme.YouPlayTheme
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.util.callIf
import com.github.eliascoelho911.youplay.util.on
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun RoomDetailsScreen(
    state: RoomDetailsState,
    currentRoomResource: Resource<Room>,
    currentMusicResource: Resource<Music>,
    optionsItems: List<OptionData>,
    backgroundColor: Color,
    onUpdateRoomName: (String) -> Unit,
    onClickOptions: () -> Unit,
    onClickExitFromRoom: () -> Unit,
    onConfirmExitFromRoom: () -> Unit,
    onDismissExitFromRoom: () -> Unit,
    onTimeChange: (Int) -> Unit,
    onClickShuffleButton: () -> Unit,
    onClickSkipToPreviousMusicButton: () -> Unit,
    onClickPlayOrPauseButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
    onClickRepeatButton: () -> Unit,
) {
    RoomDetailsContent(
        currentRoomResource,
        currentMusicResource,
        optionsItems,
        state,
        backgroundColor,
        onClickExitFromRoom,
        onConfirmExitFromRoom,
        onDismissExitFromRoom,
        onClickOptions,
        onUpdateRoomName,
        onTimeChange,
        onClickShuffleButton,
        onClickSkipToPreviousMusicButton,
        onClickPlayOrPauseButton,
        onClickSkipToNextMusicButton,
        onClickRepeatButton,
    )
}


@Composable
private fun TopBar(
    room: Room,
    onUpdateRoomName: (String) -> Unit,
    onClickExitFromRoom: () -> Unit,
    onClickOptions: () -> Unit,
) {
    var title by remember { mutableStateOf(room.name) }
    title = room.name
    AppTopBarWithCentralizedTitle(modifier = Modifier.statusBarsPadding(),
        iconBack = Icons.Rounded.Close,
        title = {
            RoomTitle(title = title, onValueChange = { newTitle ->
                title = newTitle
            }, onUpdateTitle = onUpdateRoomName)
        },
        onBackPressed = onClickExitFromRoom,
        actions = {
            OptionsButton(onClickOptions)
        })
}

@Composable
private fun RoomDetailsContent(
    currentRoomResource: Resource<Room>,
    currentMusicResource: Resource<Music>,
    optionsItems: List<OptionData>,
    state: RoomDetailsState,
    backgroundColor: Color,
    onClickExitFromRoom: () -> Unit,
    onConfirmExitFromRoom: () -> Unit,
    onDismissExitFromRoom: () -> Unit,
    onClickOptions: () -> Unit,
    onUpdateRoomName: (String) -> Unit,
    onTimeChange: (Int) -> Unit,
    onClickShuffleButton: () -> Unit,
    onClickSkipToPreviousMusicButton: () -> Unit,
    onClickPlayOrPauseButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
    onClickRepeatButton: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Background(color = backgroundColor)

        Scaffold(topBar = {
            currentRoomResource.on(success = { room ->
                TopBar(
                    room = room,
                    onUpdateRoomName = onUpdateRoomName,
                    onClickExitFromRoom = onClickExitFromRoom,
                    onClickOptions = onClickOptions)
            })
        }) {
            currentRoomResource.on(success = { currentRoom ->
                BottomDrawer(drawerContent = {
                    Options(Modifier.padding(vertical = 24.dp), room = currentRoom,
                        items = optionsItems)
                },
                    drawerState = state.bottomDrawerOptionsState,
                    drawerBackgroundColor = Black0E0E0E.copy(alpha = 0.97f),
                    drawerShape = MaterialTheme.shapes.large.copy(bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp))) {
                    Column(Modifier
                        .screenPadding(horizontal = false)
                        .navigationBarsPadding()
                        .fillMaxSize()) {
                        currentMusicResource.on(success = { currentMusic ->
                            currentMusic.album.imageUrl?.let { imageUrl ->
                                AlbumImage(Modifier.screenPadding(vertical = false), imageUrl)
                            }
                            Player(
                                currentMusic = currentMusic,
                                player = currentRoom.player,
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
            })
        }
    }

    callIf(state.exitFromRoomDialogState.isVisible()) {
        ExitFromRoomDialog(onConfirmExitFromRoom = onConfirmExitFromRoom,
            onDismissExitFromRoom = onDismissExitFromRoom)
    }

    callIf(state.loadingActionState.isVisible()) { LoadingAction() }
}

@Composable
private fun Options(modifier: Modifier = Modifier, room: Room, items: List<OptionData>) {
    Column(modifier) {
        Text(modifier = Modifier.fillMaxWidth(),
            text = room.name,
            style = typography.h5,
            textAlign = TextAlign.Center)

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) {
                OptionItem(it)
            }
        }
    }
}

@Composable
fun OptionItem(item: OptionData, onClick: () -> Unit = {}) {
    TextButton(onClick) {
        Row(Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Icon(imageVector = item.icon,
                contentDescription = item.text,
                tint = Color.White.copy(alpha = 0.5f))
            Text(text = item.text, style = typography.subtitle2)
        }
    }
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
        .size(250.dp)
        .alpha(imageAlpha)
        .align(Alignment.CenterHorizontally)
        .shadow(16.dp, shape = CircleShape),
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
private fun ExitFromRoomDialog(
    onConfirmExitFromRoom: () -> Unit,
    onDismissExitFromRoom: () -> Unit,
) {
    AlertDialog(onDismissRequest = {
        onDismissExitFromRoom()
    }, dismissButton = {
        DialogButton(onClick = {
            onDismissExitFromRoom()
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
        .padding(8.dp),
        text = text.uppercase(),
        color = colors.secondary, style = typography.button)
}

//todo: lembrar de corrigir
//@Composable
//@Preview(showBackground = true)
//private fun RoomDetailsContentPreview() {
//    YouPlayTheme {
//        RoomDetailsContent(
//            currentRoomResource = Room(name = stringResource(id = R.string.defaultRoomName,
//                "Elias"),
//                id = "",
//                player = PlayerData(),
//                currentMusicId = "",
//                ownerId = ""),
//            currentMusicResource = Resource.success(Music(id = "",
//                name = "Sonhei que tava me casando",
//                artists = listOf(Artist("WS", "Wesley Safadão")),
//                album = Album(name = "Album", imageUrl = "", id = ""),
//                durationInSeconds = 300)),
//            onClickSkipToPreviousMusicButton = {},
//            onClickSkipToNextMusicButton = {},
//            onClickPlayOrPauseButton = {},
//            onTimeChange = {},
//            onClickShuffleButton = {},
//            onClickRepeatButton = {},
//        )
//    }
//}

@Composable
@Preview(showBackground = true)
private fun ExitFromRoomDialogPreview() {
    YouPlayTheme {
        ExitFromRoomDialog(
            onConfirmExitFromRoom = {},
            onDismissExitFromRoom = {},
        )
    }
}

data class OptionData(val icon: ImageVector, val text: String, val onClick: () -> Unit = {})