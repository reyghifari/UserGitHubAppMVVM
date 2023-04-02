package com.hann.core.domain.repository

import com.hann.core.data.Resource
import com.hann.core.domain.model.DetailUser
import com.hann.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    fun getSearchUser(username :String): Flow<Resource<List<User>>>

    fun getDetailUser(username: String) : Flow<Resource<DetailUser>>

    fun getFollowing(username: String) : Flow<Resource<List<User>>>

    fun getFollower(username: String) : Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<List<User>>

    fun getFavoriteDetail(id: String): User

    fun setFavoriteUser(user: User, state: Boolean)

}