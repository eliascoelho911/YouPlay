package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.util.catchExceptions
import com.github.eliascoelho911.youplay.util.collectResource
import com.github.eliascoelho911.youplay.util.emitFailure
import com.github.eliascoelho911.youplay.util.emitLoading
import com.github.eliascoelho911.youplay.util.emitSuccess
import com.github.eliascoelho911.youplay.util.flowResource
import com.github.eliascoelho911.youplay.util.on

class ObserveCurrentMusic(
    private val observeCurrentRoom: ObserveCurrentRoom,
) {
    fun observe() = flowResource<Music> {
        observeCurrentRoom.observe().collectResource {
            on(success = { room ->
                assert(room.currentMusicId != null)

                room.currentMusicId!!.let { currentMusicId ->
                    emitSuccess(room.playlist.find { it.id == currentMusicId }!!)
                }
            }, loading = {
                emitLoading()
            }, failure = {
                emitFailure(it)
            })
        }
    }.catchExceptions()
}