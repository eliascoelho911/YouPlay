package com.github.eliascoelho911.youplay.util

import android.content.Context
import androidx.annotation.StringRes
import com.github.eliascoelho911.youplay.R

sealed class Messages(private val context: Context) {
    class Error(context: Context): Messages(context) {
        val default = getString(R.string.error_default)
        val createNewRoom = getString(R.string.error_createNewRoom)
        val enterTheRoom = getString(R.string.error_enterTheRoom)
        val roomNotFound = getString(R.string.error_roomNotFound)
        val authenticateUserOnSpotify = getString(R.string.error_authenticateUserOnSpotify)
        val userExitFromRoom = getString(R.string.error_userExitFromRoom)
    }

    protected fun getString(@StringRes id: Int) =
        context.getString(id)
}