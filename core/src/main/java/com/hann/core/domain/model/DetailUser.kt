package com.hann.core.domain.model

data class DetailUser(
    val id: Int,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
)