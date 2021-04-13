package com.topanlabs.githubuser.db

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserDao.TABLE_NAME)
data class UserEntity(

    val username: String,
    val photo: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun from(contentValues: ContentValues): UserEntity {
            val entity = UserEntity(
                username = contentValues.getAsString("username"),
                photo = contentValues.getAsString("photo")
            )
            return entity
        }
    }
}