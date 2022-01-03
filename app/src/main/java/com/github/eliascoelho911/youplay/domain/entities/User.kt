package com.github.eliascoelho911.youplay.domain.entities

data class User(
    val id: ID = "",
    val fullName: String = "",
    val imageUrl: String? = null,
) {
    val firstName: String = fullName.split(" ").firstOrNull() ?: fullName
}