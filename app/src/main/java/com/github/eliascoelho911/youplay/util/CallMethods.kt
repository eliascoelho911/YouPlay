package com.github.eliascoelho911.youplay.util

inline fun callIf(b: Boolean, block: () -> Unit) {
    if (b) block()
}