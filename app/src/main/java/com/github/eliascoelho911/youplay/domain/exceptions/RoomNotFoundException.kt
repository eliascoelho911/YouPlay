package com.github.eliascoelho911.youplay.domain.exceptions

class RoomNotFoundException(roomId: String) : NoSuchElementException(
    "could not find a room with id $roomId"
)