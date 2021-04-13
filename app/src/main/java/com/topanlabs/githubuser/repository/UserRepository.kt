package com.topanlabs.githubuser.repository

import android.database.Cursor
import androidx.annotation.WorkerThread
import com.topanlabs.githubuser.db.UserDao
import com.topanlabs.githubuser.db.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allLikedUsers: Flow<List<UserEntity>> = userDao.getLikedUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }

    fun insertProv(user: UserEntity): Long {
        return userDao.insertProv(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(user: UserEntity) {
        userDao.delete(user)
    }


    fun deleteById(id: Int): Int {
        return userDao.deleteById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun searchUser(username: String): Int {
        return userDao.searchUser(username)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUser(username: String): UserEntity {
        return userDao.getUser(username)
    }

    fun getLikedCursor(): Cursor {
        return userDao.getLikedCursor()
    }
}