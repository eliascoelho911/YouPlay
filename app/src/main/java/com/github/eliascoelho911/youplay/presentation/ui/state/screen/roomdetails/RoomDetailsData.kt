package com.github.eliascoelho911.youplay.presentation.ui.state.screen.roomdetails

import androidx.lifecycle.LiveData
import com.github.eliascoelho911.youplay.domain.entities.Music
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.util.Resource

interface RoomDetailsData {
    val currentRoomResource: LiveData<Resource<Room>>
    val currentMusicResource: LiveData<Resource<Music>>
}