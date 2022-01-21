package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.global.Messages
import com.github.eliascoelho911.youplay.global.collectResource
import com.github.eliascoelho911.youplay.global.catchExceptions
import com.github.eliascoelho911.youplay.global.emitFailure
import com.github.eliascoelho911.youplay.global.emitLoading
import com.github.eliascoelho911.youplay.global.emitSuccess
import com.github.eliascoelho911.youplay.global.flowResource
import com.github.eliascoelho911.youplay.global.on
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage

class ObserveCurrentMusic(
    private val observeCurrentRoom: ObserveCurrentRoom,
    private val errorMessages: Messages.Error,
) {
    fun observe() = flowResource<Music> {
        runChangingExceptionMessage(message = errorMessages.default) {
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
        }
    }.catchExceptions()
}