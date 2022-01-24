package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import com.github.eliascoelho911.youplay.presentation.ui.base.screens.roomdetails.OptionData

class RoomDetailsViewModel(
    private val userExitFromRoom: UserExitFromRoom,
    private val updateCurrentRoom: UpdateCurrentRoom,
    private val observeCurrentRoom: ObserveCurrentRoom,
    private val observeCurrentMusic: ObserveCurrentMusic,
    context: Context
) : ViewModel() {

    val currentRoomResource by lazy { observeCurrentRoom.observe().asLiveData() }
    val currentMusicResource by lazy { observeCurrentMusic.observe().asLiveData() }

    val optionsItems: List<OptionData> = createOptionsItems(context)

    suspend fun userExitFromRoom() {
        userExitFromRoom.exit()
    }

    suspend fun updateCurrentRoomName(name: String) {
        updateCurrentRoom.update { copy(name = name) }
    }

    private fun createOptionsItems(context: Context): List<OptionData> {
        val share = context.getString(R.string.option_share)
        val addMusic = context.getString(R.string.option_addMusic)
        val editRoomName = context.getString(R.string.option_editRoomName)
        val seeUsersInRoom = context.getString(R.string.option_seeUsersInRoom)

        return listOf(OptionData(icon = Icons.Outlined.Share, text = share),
            OptionData(icon = Icons.Outlined.AddCircle, text = addMusic),
            OptionData(icon = Icons.Outlined.Edit, text = editRoomName),
            OptionData(icon = Icons.Outlined.Person, text = seeUsersInRoom))
    }
}

