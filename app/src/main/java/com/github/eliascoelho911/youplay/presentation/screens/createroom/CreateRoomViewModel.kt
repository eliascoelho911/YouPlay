package com.github.eliascoelho911.youplay.presentation.screens.createroom

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import java.lang.ref.WeakReference

class CreateRoomViewModel(
    private val createNewRoom: CreateNewRoom,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val context: WeakReference<Context>,
    getLoggedUser: GetLoggedUser,
) : ViewModel() {
    val loggedUser = getLoggedUser.get()

    @Throws(NoSuchElementException::class)
    suspend fun createNewRoom() {
        loggedUser.lastResult().onSuccess { loggedUser ->
            val roomName = context.get()
                ?.getString(R.string.defaultRoomName, loggedUser.firstName).orEmpty()
            val roomId = RoomIDGenerator.generate()
            createNewRoom.create(roomId, roomName)
            putCurrentRoomId.put(roomId)
        }.onFailure {
            throw it
        }
    }
}
