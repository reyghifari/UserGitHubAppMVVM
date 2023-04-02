package com.hann.core.data.source.local


import com.hann.core.data.source.local.dao.UserDao
import com.hann.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val userDao: UserDao) {

    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavoriteUser()

    fun getFavoriteDetail(id: String) : UserEntity = userDao.getFavoriteDetail(id)

    suspend fun insertUser(user: List<UserEntity>) = userDao.insertUser(user)

    fun setFavoriteUser(user: UserEntity, newState: Boolean) {
        user.isFavorite = newState
        userDao.updateFavoriteUser(user)
    }

}