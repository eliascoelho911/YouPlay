package com.github.eliascoelho911.youplay.global

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<Resource<T>>.observeResource(
    lifecycleOwner: LifecycleOwner,
    observer: Resource<T>.() -> Unit,
) {
    observe(lifecycleOwner) {
        observer(it)
    }
}