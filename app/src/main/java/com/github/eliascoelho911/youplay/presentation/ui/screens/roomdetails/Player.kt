package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.entities.Album
import com.github.eliascoelho911.youplay.domain.entities.Artist
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.RepeatMode
import com.github.eliascoelho911.youplay.presentation.ui.theme.YouPlayTheme
import com.github.eliascoelho911.youplay.presentation.ui.base.components.screenPadding
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration

//todo não é possível avançar ou retroceder várias faixas rapidamente
@Composable
fun Player(
    currentMusic: Music,
    player: PlayerData,
    onTimeChange: (Int) -> Unit,
    onClickShuffleButton: () -> Unit,
    onClickSkipToPreviousMusicButton: () -> Unit,
    onClickPlayOrPauseButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
    onClickRepeatButton: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()) {
            Column(modifier = Modifier
                .screenPadding(vertical = false)
                .fillMaxWidth()) {
                MusicNameAndArtist(currentMusic)

                Spacer(modifier = Modifier.height(SliderMargin))

                Timeline(timeInSeconds = player.timeInSeconds,
                    onTimeChange = onTimeChange,
                    currentMusic = currentMusic)
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                ShuffleButton(onClick = onClickShuffleButton,
                    checked = player.shufflePlayback)
                MusicControls(
                    onClickSkipToPreviousMusicButton = onClickSkipToPreviousMusicButton,
                    playingMusic = player.playingMusic,
                    onClickPlayOrPauseButton = onClickPlayOrPauseButton,
                    onClickSkipToNextMusicButton = onClickSkipToNextMusicButton,
                )
                RepeatButton(repeatMode = player.repeatMode, onClick = onClickRepeatButton)
            }
        }
    }
}

@Composable
private fun MusicNameAndArtist(currentMusic: Music) {
    Text(text = currentMusic.name,
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onBackground,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
    Spacer(modifier = Modifier.height(CurrentMusicArtistMargin))
    Text(text = currentMusic.artists.firstOrNull()?.name.orEmpty(),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.high),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}

@Composable
private fun BoxScope.ShuffleButton(
    checked: Boolean,
    onClick: () -> Unit,
) {
    PlayerIconToggleButton(modifier = Modifier.align(Alignment.CenterStart),
        checked = checked,
        onCheckedChange = { onClick() },
        painter = painterResource(id = R.drawable.ic_shuffle),
        contentDescription = stringResource(id = R.string.roomDetails_shuffleButtonDescription))
}

@Composable
private fun BoxScope.RepeatButton(
    repeatMode: RepeatMode,
    onClick: () -> Unit,
) {
    val drawable = when (repeatMode) {
        RepeatMode.NONE, RepeatMode.ALL -> R.drawable.ic_repeat
        RepeatMode.ONE -> R.drawable.ic_repeat_one
    }
    PlayerIconToggleButton(modifier = Modifier.align(Alignment.CenterEnd),
        checked = repeatMode != RepeatMode.NONE,
        onCheckedChange = { onClick() },
        painter = painterResource(id = drawable),
        contentDescription = stringResource(id = R.string.roomDetails_repeatButtonDescription))
}

@Composable
private fun BoxScope.MusicControls(
    onClickSkipToPreviousMusicButton: () -> Unit,
    playingMusic: Boolean,
    onClickPlayOrPauseButton: () -> Unit,
    onClickSkipToNextMusicButton: () -> Unit,
) {
    Row(modifier = Modifier.align(Alignment.Center),
        verticalAlignment = Alignment.CenterVertically) {
        SkipPreviousButton(onClick = onClickSkipToPreviousMusicButton)
        Spacer(modifier = Modifier.width(MusicControlsButtonMargin))
        PlayOrPauseButton(playingMusic = playingMusic,
            onClick = onClickPlayOrPauseButton)
        Spacer(modifier = Modifier.width(MusicControlsButtonMargin))
        SkipNextButton(onClick = onClickSkipToNextMusicButton)
    }
}

@Composable
private fun PlayerIconToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    painter: Painter,
    contentDescription: String,
) {
    IconToggleButton(modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange) {
        Icon(painter = painter,
            contentDescription = contentDescription,
            tint = if (!checked) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondary)
    }
}

@Composable
private fun SkipPreviousButton(onClick: () -> Unit) {
    SkipButton(onClick = onClick,
        painter = painterResource(id = R.drawable.ic_skip_previous),
        contentDescription = stringResource(
            id = R.string.roomDetails_skipPreviousButtonDescription))
}

@Composable
private fun PlayOrPauseButton(playingMusic: Boolean, onClick: () -> Unit) {
    val contentDescription: String
    val painter: Painter
    if (!playingMusic) {
        painter = painterResource(id = R.drawable.ic_play_circle)
        contentDescription = stringResource(id = R.string.roomDetails_playButtonDescription)
    } else {
        painter = painterResource(id = R.drawable.ic_pause_circle)
        contentDescription = stringResource(id = R.string.roomDetails_pauseButtonDescription)
    }
    PlayerIconButton(modifier = Modifier.size(PlayOrPauseButtonSize),
        onClick = onClick,
        painter = painter,
        contentDescription = contentDescription,
        iconSize = PlayOrPauseButtonSize)
}

@Composable
private fun SkipNextButton(onClick: () -> Unit) {
    SkipButton(onClick = onClick,
        painter = painterResource(id = R.drawable.ic_skip_next),
        contentDescription = stringResource(
            id = R.string.roomDetails_skipNextButtonDescription))
}

@Composable
private fun SkipButton(
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String,
) {
    PlayerIconButton(onClick = onClick,
        painter = painter,
        contentDescription = contentDescription,
        iconSize = SkipButtonIconSize)
}


@Composable
private fun PlayerIconButton(
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    enabled: Boolean = true,
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String,
) {
    val indication: Indication = rememberRipple(bounded = false,
        radius = if (iconSize > 48.dp) iconSize / 2 else PlayerButtonRippleRadius)
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = MutableInteractionSource(),
                indication = indication
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(modifier = Modifier.size(iconSize),
            painter = painter,
            contentDescription = contentDescription,
            tint = if (enabled) MaterialTheme.colors.onBackground else MaterialTheme.colors.onBackground.copy(
                alpha = ContentAlpha.disabled))
    }
}

@Composable
fun Timeline(
    timeInSeconds: Int,
    onTimeChange: (Int) -> Unit,
    currentMusic: Music?,
) {
    PlayerSlider(timeInSeconds = timeInSeconds,
        onTimeChange = onTimeChange,
        musicDuration = currentMusic?.durationInSeconds ?: 0)
    MusicTimer(timeInSeconds = timeInSeconds,
        musicDuration = currentMusic?.durationInSeconds ?: 0)
}

@Composable
private fun PlayerSlider(
    timeInSeconds: Int,
    onTimeChange: (Int) -> Unit,
    musicDuration: Int,
    enabled: Boolean = true,
) {
    Slider(
        value = timeInSeconds.toFloat(),
        onValueChange = { onTimeChange(it.roundToInt()) },
        valueRange = 0f..musicDuration.toFloat(),
        enabled = enabled
    )
}

@Composable
private fun MusicTimer(timeInSeconds: Int, musicDuration: Int) {
    Box(modifier = Modifier.fillMaxWidth()) {
        PlayerTime(modifier = Modifier.align(Alignment.CenterStart), time = timeInSeconds)
        PlayerTime(modifier = Modifier.align(Alignment.CenterEnd), time = musicDuration)
    }
}

@Composable
private fun PlayerTime(modifier: Modifier = Modifier, time: Int) {
    Text(modifier = modifier,
        text = time.format(),
        color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.high),
        style = MaterialTheme.typography.caption)
}

private fun Int.format(): String {
    toDuration(DurationUnit.SECONDS).toComponents { _, minutes, seconds, _ ->
        return "$minutes:${seconds.toString().padStart(2, '0')}"
    }
}

@Composable
@Preview
private fun PlayerPreview() {
    YouPlayTheme {
        Player(
            currentMusic = Music(id = "",
                name = "Boavista",
                durationInSeconds = 360,
                artists = listOf(
                    Artist(id = "", name = "Stephan Bodzin")),
                album = Album(id = "", name = "Innellea", imageUrl = null)),
            player = PlayerData(),
            onTimeChange = {},
            onClickShuffleButton = {},
            onClickSkipToPreviousMusicButton = {},
            onClickPlayOrPauseButton = {},
            onClickSkipToNextMusicButton = {},
            onClickRepeatButton = {}
        )
    }
}

private val CurrentMusicArtistMargin = 4.dp
private val SliderMargin = 4.dp
private val MusicControlsButtonMargin = 24.dp
private val PlayOrPauseButtonSize = 64.dp
private val SkipButtonIconSize = 40.dp
private val PlayerButtonRippleRadius = 24.dp