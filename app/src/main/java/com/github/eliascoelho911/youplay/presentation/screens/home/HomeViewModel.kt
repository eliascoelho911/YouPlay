package com.github.eliascoelho911.youplay.presentation.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.global.assertSuccess
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import java.lang.ref.WeakReference

class HomeViewModel(
    private val createNewRoom: CreateNewRoom,
    private val enterTheRoom: EnterTheRoom,
    private val context: WeakReference<Context>,
    private val getLoggedUser: GetLoggedUser,
) : ViewModel() {
    val loggedUser by lazy { getLoggedUser.get() }

    suspend fun createNewRoom(roomId: ID) {
        loggedUser.lastResult().assertSuccess { loggedUser ->
            val roomName = context.get()?.getString(
                R.string.defaultRoomName, loggedUser.firstName
            ).orEmpty()
            createNewRoom.create(roomId, roomName)
        }
    }

    suspend fun enterTheRoom(roomId: ID) {
        enterTheRoom.enter(roomId)
    }
}
