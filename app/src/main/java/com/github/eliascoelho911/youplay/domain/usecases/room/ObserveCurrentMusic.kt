package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.collectResource
import com.github.eliascoelho911.youplay.common.emitErrors
import com.github.eliascoelho911.youplay.common.emitIfLoadingOrFailure
import com.github.eliascoelho911.youplay.common.emitSuccess
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.Music

class ObserveCurrentMusic(private val observeCurrentRoom: ObserveCurrentRoom) {
    fun observe() = flowResource<Music> {
        observeCurrentRoom.observe().collectResource {
            onSuccess { room ->
                room.currentMusicId!!.let { currentMusicId ->
                    emitSuccess(room.playlist.find { it.id == currentMusicId }!!)
                }
            }
            emitIfLoadingOrFailure(this)
        }
    }.emitErrors()
}