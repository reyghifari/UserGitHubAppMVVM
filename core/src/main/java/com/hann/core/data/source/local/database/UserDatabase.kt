package com.hann.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hann.core.data.source.local.dao.UserDao
import com.hann.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

}