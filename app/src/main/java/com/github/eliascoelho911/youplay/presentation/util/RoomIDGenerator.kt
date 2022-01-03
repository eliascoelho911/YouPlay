package com.github.eliascoelho911.youplay.presentation.util

import org.apache.commons.lang3.RandomStringUtils

object RoomIDGenerator {
    const val LengthRoomUUID = 5
    const val HasLetters = true
    const val HasNumbers = true

    fun generate() = RandomStringUtils.random(LengthRoomUUID, HasLetters, HasNumbers).uppercase()
}