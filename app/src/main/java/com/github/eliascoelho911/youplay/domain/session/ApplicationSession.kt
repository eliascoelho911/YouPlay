package com.github.eliascoelho911.youplay.domain.session

interface ApplicationSession {
    suspend fun getAuthId(): String?

    suspend fun putAuthId(id: String?)

    suspend fun getCurrentRoomId(): String?

    suspend fun putCurrentRoomId(id: String?)
}