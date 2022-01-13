package com.github.eliascoelho911.youplay.domain.entities

data class Music(
    val id: ID,
    val name: String,
    val durationInSeconds: Int,
    val artists: List<Artist> = emptyList(),
    val album: Album,
)