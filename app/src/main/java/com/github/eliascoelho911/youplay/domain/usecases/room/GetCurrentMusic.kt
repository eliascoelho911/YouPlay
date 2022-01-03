package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.emitErrors
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.Music
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map

class GetCurrentMusic(private val getCurrentRoom: GetCurrentRoom) {
    fun get() = flowResource<Music> {
        emitAll(getCurrentRoom.get().map {
            when (it) {
                is Resource.Success -> {
                    val room = it.data
                    room.currentMusicId!!.let { currentMusicId ->
                        val currentMusic = room.playlist.find { it.id == currentMusicId }
                            ?: room.playlist.first()
                        Resource.success(currentMusic)
                    }
                }
                is Resource.Failed -> {
                    Resource.failure(it.throwable)
                }
                is Resource.Loading -> {
                    Resource.loading()
                }
            }
        })
    }.emitErrors()
}