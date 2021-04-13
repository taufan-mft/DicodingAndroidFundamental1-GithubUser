package com.topanlabs.consumerapp.helper

import android.database.Cursor

/**
 * Created by taufan-mft on 4/13/2021.
 */
object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserEntity> {
        val userList = ArrayList<UserEntity>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val username = getString(getColumnIndexOrThrow("username"))
                val photo = getString(getColumnIndexOrThrow("photo"))
                userList.add(UserEntity(id, username, photo))
            }
        }
        return userList
    }
}