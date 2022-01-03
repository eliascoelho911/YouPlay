package com.github.eliascoelho911.youplay.infrastructure.data.mappers

import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.infrastructure.data.documents.RoomDocument

fun RoomDocument.toEntity(id: ID) =
    Room(id = id,
        name = name,
        playlist = playlist,
        users = users,
        ownerId = ownerId,
        currentMusicId = currentMusicId,
        player = player)

fun Room.toDocument() =
    RoomDocument(name = name,
        playlist = playlist,
        users = users,
        ownerId = ownerId,
        currentMusicId = currentMusicId,
        player = player)