package com.github.eliascoelho911.youplay.presentation.screens.createroom

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import java.lang.ref.WeakReference

class CreateRoomViewModel(
    private val createNewRoom: CreateNewRoom,
    private val enterTheRoom: EnterTheRoom,
    private val context: WeakReference<Context>,
    getLoggedUser: GetLoggedUser,
) : ViewModel() {
    val loggedUser = getLoggedUser.get()

    suspend fun createNewRoom(roomId: ID) {
        loggedUser.lastResult().onSuccess { loggedUser ->
            val roomName = context.get()?.getString(
                R.string.defaultRoomName, loggedUser.firstName
            ).orEmpty()
            createNewRoom.create(roomId, roomName)
        }.onFailure {
            throw it
        }
    }

    suspend fun enterTheRoom(roomId: ID) {
        enterTheRoom.enter(roomId)
    }
}
