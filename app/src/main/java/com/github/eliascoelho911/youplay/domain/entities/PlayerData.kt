package com.github.eliascoelho911.youplay.domain.entities

data class PlayerData(
    val playingMusic: Boolean = false,
    val shufflePlayback: Boolean = false,
    val timeInSeconds: Int = 0,
    val repeatMode: RepeatMode = RepeatMode.NONE,
)

private const val AllId = 1
private const val OneId = 2
private const val NoneId = 3

enum class RepeatMode(private val id: Int, private val nextId: Int) {
    ALL(id = AllId, nextId = NoneId),
    NONE(id = NoneId, nextId = OneId),
    ONE(id = OneId, nextId = AllId);

    fun next(): RepeatMode {
        return values().first { it.id == this.nextId }
    }
}