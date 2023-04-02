package com.hann.core.data.source.remote.response

data class DetailResponse(
    val id: Int,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
)


