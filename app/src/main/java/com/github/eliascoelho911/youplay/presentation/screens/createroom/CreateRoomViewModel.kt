package com.github.eliascoelho911.youplay.presentation.screens.createroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import kotlinx.coroutines.launch

class CreateRoomViewModel(
    private val createNewRoom: CreateNewRoom,
    private val putCurrentRoomId: PutCurrentRoomId,
    getLoggedUser: GetLoggedUser,
//    private val createNewRoom: CreateNewRoomWithDefaultName,
//    private val accessRoom: AccessRoom,
) : ViewModel() {
    val loggedUser = getLoggedUser.loggedUser

    fun createNewRoom(name: String) {
        viewModelScope.launch {
            createNewRoom.invoke(id = RoomIDGenerator.generate(), name).let { createdRoomId ->
                putCurrentRoomId.invoke(createdRoomId)
            }
        }
    }
//
//    //todo: o acesso a sala deve ser feito dentro da screen RoomDetails
//    fun accessRoom(roomId: String) =
//        accessRoom.invoke(roomId).asEvent()
}
