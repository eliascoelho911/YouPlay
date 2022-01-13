package com.github.eliascoelho911.youplay.presentation.screens.createroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CreateRoomViewModel(
    private val createNewRoom: CreateNewRoom,
    private val putCurrentRoomId: PutCurrentRoomId,
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

    fun enterTheRoom(roomId: ID) {
        viewModelScope.launch {
            putCurrentRoomId.put(roomId)
        }
    }
}
