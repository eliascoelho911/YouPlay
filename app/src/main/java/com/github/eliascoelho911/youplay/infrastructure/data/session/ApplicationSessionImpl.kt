package com.github.eliascoelho911.youplay.infrastructure.data.session

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val SharedPreferencesKey = "session"
private const val AuthIdKey = "auth_id"
private const val CurrentRoomIdKey = "current_room_key"

class ApplicationSessionImpl(context: Context) : ApplicationSession {
    private val sharedPreferences = context.getSharedPreferences(SharedPreferencesKey, MODE_PRIVATE)

    override suspend fun getAuthId() = withContext(Dispatchers.IO) {
        sharedPreferences.getString(AuthIdKey, null)
    }

    override suspend fun putAuthId(id: String?) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit {
                putString(AuthIdKey, id)
            }
        }
    }

    override suspend fun getCurrentRoomId() = withContext(Dispatchers.IO) {
        sharedPreferences.getString(CurrentRoomIdKey, null)
    }

    override suspend fun putCurrentRoomId(id: String?) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit {
                putString(CurrentRoomIdKey, id)
            }
        }
    }
}