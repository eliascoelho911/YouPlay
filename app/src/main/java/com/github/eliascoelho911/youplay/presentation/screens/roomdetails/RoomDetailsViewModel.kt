package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom

class RoomDetailsViewModel(
//    private val updateRoomName: UpdateRoomName,
//    private val leaveTheCurrentRoom: LeaveTheCurrentRoom,
//    private val skipToPreviousMusic: SkipToPreviousMusic,
//    private val skipToNextMusic: SkipToNextMusic,
//    private val togglePlaybackState: TogglePlaybackState,
//    private val toggleShuffleState: ToggleShuffleState,
//    private val changeTimelineTime: ChangeTimelineTime,
//    private val nextRepeatMode: NextRepeatMode,
//    findDominantColorInCurrentMusic: FindDominantColorInCurrentMusic,
//    userIsInSomeRoom: UserIsInSomeRoom,
    getCurrentRoom: GetCurrentRoom,
    getCurrentMusic: GetCurrentMusic
//    fetchCurrentMusic: FetchCurrentMusic,
) : ViewModel() {
//    var userIsInSomeRoom: Boolean = false
//
//    init {
//        viewModelScope.launch {
//            this@RoomDetailsViewModel.userIsInSomeRoom = userIsInSomeRoom.invoke()
//        }
//    }

    val currentRoom = getCurrentRoom.get()
    val currentMusic = getCurrentMusic.get()
//    val dominantColorInCurrentMusic = findDominantColorInCurrentMusic.invoke()
//
//    fun updateRoomName(newName: String) =
//        updateRoomName.invoke(newName).asEvent()
//
//    fun leaveTheCurrentRoom() =
//        leaveTheCurrentRoom.invoke().asEvent()
//
//    fun skipToPreviousMusic() =
//        skipToPreviousMusic.invoke().asEvent()
//
//    fun skipToNextMusic() =
//        skipToNextMusic.invoke().asEvent()
//
//    fun togglePlaybackState() =
//        togglePlaybackState.invoke().asEvent()
//
//    fun toggleShuffleState() =
//        toggleShuffleState.invoke().asEvent()
//
//    fun changeTimelineTime(newTimeInSeconds: Int) =
//        changeTimelineTime.invoke(newTimeInSeconds).asEvent()
//
//    fun nextRepeatMode() =
//        nextRepeatMode.invoke().asEvent()
}