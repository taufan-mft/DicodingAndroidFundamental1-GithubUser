package com.topanlabs.githubuser.db

import android.database.Cursor
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

    @Insert
    fun insertProv(userEntity: UserEntity): Long

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE id =:id")
    fun deleteById(id: Int): Int

    @Query("SELECT COUNT(id) FROM $TABLE_NAME WHERE username =:username ")
    suspend fun searchUser(username: String): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE username=:username ")
    suspend fun getUser(username: String): UserEntity

    @Query("SELECT * FROM $TABLE_NAME ORDER BY username ASC")
    fun getLikedCursor(): Cursor


}