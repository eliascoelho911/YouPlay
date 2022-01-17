package com.github.eliascoelho911.youplay.common

import android.content.Context
import androidx.annotation.StringRes
import com.github.eliascoelho911.youplay.R

sealed class Messages(private val context: Context) {
    class Error(context: Context): Messages(context) {
        val createNewRoom = getString(R.string.error_createNewRoom)
    }

    protected fun getString(@StringRes id: Int) =
        context.getString(id)
}