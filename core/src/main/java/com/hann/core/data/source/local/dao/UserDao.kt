package com.hann.core.data.source.local.dao

import androidx.room.*
import com.hann.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user where isFavorite = 1")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE userId = :id")
    fun getFavoriteDetail(id : String): UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>)

    @Update
    fun updateFavoriteUser(user: UserEntity)

}