package com.github.eliascoelho911.youplay.presentation.util

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.youplay.R

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    iconBack: ImageVector = Icons.Rounded.ArrowBack,
    onBackPressed: (() -> Unit)? = null,
    title: @Composable (BoxScope.() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = TopBarMinHeight, max = TopBarMaxHeight)) {
        Box(Modifier
            .align(Alignment.CenterStart)
            .padding(vertical = 16.dp)
            .fillMaxWidth()) {
            title?.invoke(this)
        }
        onBackPressed?.let { onBackPressed ->
            BackHandler(onBack = onBackPressed)
            IconButton(modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackPressed) {
                Icon(imageVector = iconBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = colors.onBackground
                )
            }
        }
        Box(Modifier.align(Alignment.CenterEnd)) {
            actions?.invoke()
        }
    }
}

@Composable
fun AppTopBarWithCentralizedTitle(
    modifier: Modifier = Modifier,
    iconBack: ImageVector = Icons.Rounded.ArrowBack,
    onBackPressed: (() -> Unit)? = null,
    title: @Composable BoxScope.() -> Unit,
    actions: @Composable (() -> Unit)? = null,
) {
    AppTopBar(modifier, iconBack, onBackPressed, title = {
        Box(Modifier.align(Alignment.Center)) {
            title()
        }
    }, actions)
}

private val TopBarMinHeight = 56.dp
private val TopBarMaxHeight = 128.dp