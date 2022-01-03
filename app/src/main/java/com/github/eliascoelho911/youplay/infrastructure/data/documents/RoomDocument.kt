package com.github.eliascoelho911.youplay.infrastructure.data.documents

import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Playlist

data class RoomDocument(
    val name: String = "",
    val playlist: Playlist = emptyList(),
    val users: List<ID> = emptyList(),
    val ownerId: ID = "",
    val currentMusicId: String? = null,
    val player: PlayerData = PlayerData(),
)