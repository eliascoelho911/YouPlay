package com.github.eliascoelho911.youplay.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.util.on
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.presentation.ui.base.components.AppTopBar
import com.github.eliascoelho911.youplay.presentation.ui.base.components.ButtonWithLoading
import com.github.eliascoelho911.youplay.presentation.ui.base.components.ShapeProgressIndicator
import com.github.eliascoelho911.youplay.presentation.ui.base.components.screenPadding
import com.github.eliascoelho911.youplay.presentation.ui.theme.Purple2C3863
import com.github.eliascoelho911.youplay.presentation.ui.theme.RedEC5462
import com.github.eliascoelho911.youplay.presentation.ui.theme.YouPlayTheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    createRoomButtonIsLoading: Boolean,
    onClickToCreateRoom: () -> Unit,
    onClickToEnterRoom: () -> Unit,
) {
    val user by viewModel.loggedUser.collectAsState(initial = Resource.loading())
    Scaffold(topBar = { HomeTopBar(user) },
        modifier = Modifier
            .background(colors.background)
            .fillMaxSize()
    ) {
        HomeContent(user, createRoomButtonIsLoading, onClickToCreateRoom, onClickToEnterRoom)
    }
}

@Composable
private fun HomeTopBar(user: Resource<User>) {
    AppTopBar(modifier = Modifier.statusBarsPadding(),
        title = { Salutation(user = user) },
        actions = {
            ProfileImage(user = user)
        })
}

@Composable
private fun HomeContent(
    user: Resource<User>,
    createRoomButtonIsLoading: Boolean,
    onClickCreateRoomButton: () -> Unit,
    onClickEnterTheRoom: () -> Unit,
) {
    Column(Modifier
        .screenPadding(vertical = false)
        .fillMaxSize()
        .navigationBarsPadding()) {
        CreateRoomCard(user, createRoomButtonIsLoading, onClickCreateRoomButton)
        EnterRoomClickableMessage(onClickEnterTheRoom, user)
    }
}

@Composable
private fun BoxScope.Salutation(user: Resource<User>) {
    Box(Modifier
        .screenPadding(start = true)
        .align(Alignment.CenterStart)) {
        user.on(success = { user ->
            Text(text = buildAnnotatedString {
                withStyle(typography.body1.toSpanStyle().copy(color = colors.onBackground)) {
                    append(stringResource(id = R.string.home_salutation))
                }
                append("\n")
                withStyle(typography.h6.toSpanStyle().copy(color = colors.onBackground)) {
                    append(user.fullName)
                }
            })
        }, loading = { SalutationProgressIndicator() },
            failure = { SalutationProgressIndicator() })
    }
}

@Composable
private fun SalutationProgressIndicator() {
    Column {
        ShapeProgressIndicator(Modifier.size(30.dp, 20.dp))
        Spacer(Modifier.size(0.dp, 4.dp))
        ShapeProgressIndicator(Modifier.size(90.dp, 20.dp))
    }
}

@Composable
private fun ProfileImage(user: Resource<User>) {
    Box(Modifier.screenPadding(end = true)) {
        user.on(success = { user ->
            if (user.imageUrl != null) {
                Surface(modifier = Modifier
                    .size(ProfileImageSize),
                    shape = shapes.small,
                    color = Color.White.copy(alpha = 0.1f)) {
                    Image(painter = rememberImagePainter(data = user.imageUrl),
                        contentDescription = stringResource(id = R.string.profileImage))
                }
            } else {
                Box(Modifier
                    .background(linearGradient(
                        0.17f to colors.secondary,
                        1f to Purple2C3863,
                    ), shape = shapes.small)
                    .clip(shapes.small)
                    .size(ProfileImageSize)
                ) {
                    Text(modifier = Modifier.align(
                        Alignment.Center
                    ), text = user.fullName.first().uppercase(),
                        style = typography.h6,
                        color = colors.onPrimary)
                }
            }
        }, loadingOrFailure = {
            ProfileImageProgressIndicator()
        })
    }
}

@Composable
private fun ProfileImageProgressIndicator() {
    ShapeProgressIndicator(modifier = Modifier
        .size(ProfileImageSize),
        shape = shapes.small)
}

@Composable
private fun CreateRoomCard(
    user: Resource<User>,
    createRoomButtonIsLoading: Boolean,
    onClickCreateRoomButton: () -> Unit,
) {
    user.on(success = {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(linearGradient(
                0.17f to RedEC5462,
                1f to Purple2C3863,
            ), shapes.medium)) {
            CreateRoomCardContent(createRoomButtonIsLoading, onClickCreateRoomButton)
        }
    }, loadingOrFailure = { CreateRoomProgressIndicator() })
}

@Composable
private fun CreateRoomCardContent(buttonIsLoading: Boolean, onClickCreateRoomButton: () -> Unit) {
    Column(Modifier
        .padding(CreateRoomCardInternalMargin)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.home_cardTitle),
            style = typography.h6,
            fontWeight = FontWeight.Bold, color = colors.surface)

        Spacer(Modifier.height(CreateRoomCardTextMargin))

        Text(text = stringResource(id = R.string.home_cardMessage),
            style = typography.body2, color = colors.surface.copy(alpha = ContentAlpha.medium))

        Spacer(Modifier.height(CreateRoomCardButtonMargin))

        ButtonWithLoading(
            loading = buttonIsLoading, onClick = onClickCreateRoomButton,
            buttonContent = {
                Text(text = stringResource(id = R.string.home_cardButton).uppercase(),
                    style = typography.button, color = colors.background)
            },
        )
    }
}

@Composable
private fun CreateRoomProgressIndicator() {
    ShapeProgressIndicator(Modifier
        .fillMaxWidth()
        .height(137.dp), shape = shapes.medium)
}

@Composable
private fun EnterRoomClickableMessage(
    onClickEnterTheRoom: () -> Unit,
    user: Resource<User>,
) {
    Box(Modifier.fillMaxWidth()) {
        user.on(success = {
            TextButton(modifier = Modifier
                .align(Alignment.Center),
                onClick = onClickEnterTheRoom) {
                Text(text = stringResource(id = R.string.home_enterRoom),
                    color = colors.onBackground,
                    style = typography.subtitle2)
            }
        }, loadingOrFailure = { EnterRoomClickableMessageProgressIndicator() })
    }
}

@Composable
private fun BoxScope.EnterRoomClickableMessageProgressIndicator() {
    Column(Modifier.align(Alignment.Center)) {
        Spacer(Modifier.height(EnterRoomTextPadding))
        ShapeProgressIndicator(Modifier
            .size(300.dp, 15.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentWithCreateRoomButtonIsLoading() {
    YouPlayTheme {
        HomeContent(
            user = Resource.Success(User("Elias Costa")),
            onClickCreateRoomButton = {},
            onClickEnterTheRoom = {},
            createRoomButtonIsLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentWithNotCreateRoomButtonIsLoading() {
    YouPlayTheme {
        HomeContent(
            user = Resource.Success(User("Elias Costa")),
            onClickCreateRoomButton = {},
            onClickEnterTheRoom = {},
            createRoomButtonIsLoading = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentWithUserLoadingPreview() {
    YouPlayTheme {
        HomeContent(
            user = Resource.Loading(),
            onClickCreateRoomButton = {},
            onClickEnterTheRoom = {},
            createRoomButtonIsLoading = true
        )
    }
}

private val CreateRoomCardInternalMargin = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
private val CreateRoomCardTextMargin = 8.dp
private val EnterRoomTextPadding = 16.dp
private val CreateRoomCardButtonMargin = 16.dp
private val ProfileImageSize = 36.dp