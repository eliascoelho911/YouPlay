package com.github.eliascoelho911.youplay.domain.entities

data class Room(
    var id: ID,
    val name: String,
    val playlist: Playlist = emptyList(),
    val users: List<ID> = emptyList(),
    val ownerId: ID,
    val currentMusicId: String?,
    val player: PlayerData,
)

fun Room.copyAddingUsers(vararg users: ID) = copy(users = this.users.toMutableList().apply {
    addAll(users)
})

fun Room.copyRemovingUsers(vararg users: ID) = copy(users = this.users.toMutableList().apply {
    removeAll(users)
})

