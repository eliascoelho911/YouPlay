package com.github.eliascoelho911.youplay.domain.entities

data class Room(var id: ID,
                val name: String,
                val playlist: Playlist = emptyList(),
                val users: List<ID> = emptyList(),
                val ownerId: ID,
                val currentMusicId: String?,
                val player: PlayerData)
