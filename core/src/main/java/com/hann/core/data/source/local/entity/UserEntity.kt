package com.hann.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val id: Int,

    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite : Boolean
)