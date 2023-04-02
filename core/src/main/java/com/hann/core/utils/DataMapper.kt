package com.hann.core.utils

import com.hann.core.data.source.local.entity.UserEntity
import com.hann.core.data.source.remote.response.DetailResponse
import com.hann.core.data.source.remote.response.FollowersResponseItem
import com.hann.core.data.source.remote.response.FollowingResponseItem
import com.hann.core.data.source.remote.response.UserResponse
import com.hann.core.domain.model.DetailUser
import com.hann.core.domain.model.User

object DataMapper {

    fun mapResponsesToEntities(input: List<UserResponse>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                id = it.id,
                login = it.login,
                isFavorite = false,
                avatar_url = it.avatar_url
            )
            userList.add(user)
        }
        return userList
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                login = it.login,
                isFavorite = false,
                avatar_url = it.avatar_url,
            )
        }

    fun mapDomainToEntity(input: User) = UserEntity(
        id = input.id,
        avatar_url = input.avatar_url,
        login = input.login,
        isFavorite = input.isFavorite
    )

    fun mapEntityToDomain(input: UserEntity) = User(
        id = input.id,
        avatar_url = input.avatar_url,
        login = input.login,
        isFavorite = input.isFavorite
    )

    fun mapResponseToDomain(input: DetailResponse) = DetailUser(
        id = input.id,
        avatar_url = input.avatar_url,
        login = input.login,
        following = input.following,
        followers = input.followers,
        name = input.name
    )

    fun mapResponsesToDomainFollowing(input: List<FollowingResponseItem>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                id = it.id,
                login = it.login,
                isFavorite = false,
                avatar_url = it.avatar_url
            )
            userList.add(user)
        }
        return userList
    }

    fun mapResponsesToDomainFollower(input: List<FollowersResponseItem>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                id = it.id,
                login = it.login,
                isFavorite = false,
                avatar_url = it.avatar_url
            )
            userList.add(user)
        }
        return userList
    }


}