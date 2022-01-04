package com.github.eliascoelho911.youplay.presentation.screens.createroom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.common.collectResource
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CreateRoomViewModel(
    private val createNewRoom: CreateNewRoom,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val context: WeakReference<Context>,
    getLoggedUser: GetLoggedUser,
//    private val createNewRoom: CreateNewRoomWithDefaultName,
//    private val accessRoom: AccessRoom,
) : ViewModel() {
    val loggedUser = getLoggedUser.loggedUser

    @Throws(NoSuchElementException::class)
    fun createNewRoom() {
        viewModelScope.launch {
            loggedUser.collectResource {
                onSuccess { loggedUser ->
                    val roomName = context.get()
                        ?.getString(R.string.defaultRoomName, loggedUser.firstName).orEmpty()
                    val roomId = RoomIDGenerator.generate()
                    createNewRoom.invoke(roomId, roomName)
                    putCurrentRoomId.invoke(roomId)
                }
                onFailure {
                    throw it
                }
            }
        }
    }
//
//    //todo: o acesso a sala deve ser feito dentro da screen RoomDetails
//    fun accessRoom(roomId: String) =
//        accessRoom.invoke(roomId).asEvent()
}
