package com.github.eliascoelho911.youplay.infrastructure.data.mappers

import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.infrastructure.data.bodies.SpotifyUserBody

fun SpotifyUserBody.toDomainUser(): User =
    User(id = id, fullName = fullName, imageUrl = images.firstOrNull { it.url != null }?.url)