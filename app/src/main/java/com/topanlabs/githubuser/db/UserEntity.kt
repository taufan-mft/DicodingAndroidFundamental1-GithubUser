package com.topanlabs.githubuser.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserDao.TABLE_NAME)
data class UserEntity(


    val username: String,
    val photo: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}