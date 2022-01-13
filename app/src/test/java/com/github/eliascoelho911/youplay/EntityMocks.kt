package com.github.eliascoelho911.youplay

import com.github.eliascoelho911.youplay.domain.entities.Album
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.User

val musicMock = Music(id = "musicId",
    name = "name",
    durationInSeconds = 0,
    album = Album(id = "albumId", name = "name", imageUrl = null))

val userMock = User(id = "userId", fullName = "fullName", imageUrl = null)

val roomMock = Room(id = "id",
    name = "name",
    ownerId = "ownerId",
    users = listOf(userMock.id),
    playlist = listOf(musicMock),
    currentMusicId = musicMock.id,
    player = PlayerData())
