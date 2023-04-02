package com.hann.githubappmvvm.data.source.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.hann.core.data.source.local.dao.UserDao
import com.hann.core.data.source.local.database.UserDatabase
import com.hann.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.userDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val userList = listOf(
            UserEntity(1, "url", "han", false),
            UserEntity(2, "url", "ghif", false)
        )
        dao.insertUser(userList)
        val allGetUser = dao.getAllUser().first()
        assertThat(allGetUser).isEqualTo(userList)
    }

    @Test
    fun getFavoriteUserById() = runBlockingTest {
        val userList = listOf(
            UserEntity(1, "url", "han", false)
        )
        dao.insertUser(userList)
        val user = dao.getFavoriteDetail("1")

        assertThat(userList).contains(user)
    }


    @Test
    fun getFavoriteAllUser() = runBlockingTest {
        val userList = listOf(
            UserEntity(1, "url", "han", true),
            UserEntity(2, "url", "ghif", true),
            UserEntity(3, "url", "raihan", false),
        )
        dao.insertUser(userList)
        val userNotFavorite = dao.getFavoriteDetail("3")
        val user = dao.getFavoriteUser().first()

        assertThat(user).doesNotContain(userNotFavorite)
    }





}