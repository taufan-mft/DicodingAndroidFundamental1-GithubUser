package com.topanlabs.githubuser.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    companion object {
        const val TABLE_NAME = "user_table"
    }

    @Query("SELECT * FROM $TABLE_NAME ORDER BY username ASC")
    fun getLikedUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT COUNT(id) FROM $TABLE_NAME WHERE username =:username ")
    suspend fun searchUser(username: String): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE username=:username ")
    suspend fun getUser(username: String): UserEntity

}