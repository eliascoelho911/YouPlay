package com.github.eliascoelho911.youplay.infrastructure.data.bodies

import com.google.gson.annotations.SerializedName

data class SpotifyUserBody(
    @SerializedName("display_name") val fullName: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<SpotifyImageBody>,
)