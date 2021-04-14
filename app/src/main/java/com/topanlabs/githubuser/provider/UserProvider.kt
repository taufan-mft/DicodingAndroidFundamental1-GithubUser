package com.topanlabs.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.topanlabs.githubuser.db.UserEntity
import com.topanlabs.githubuser.db.UserRoomDatabase
import com.topanlabs.githubuser.repository.UserRepository

class UserProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.topanlabs.githubuser"
        const val SCHEME = "content"
        const val TABLE_NAME = "user_table"
        const val USER_CODE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userRepository: UserRepository
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    init {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER_CODE)
    }

    override fun onCreate(): Boolean {
        val database = UserRoomDatabase.getDatabase(context!!)
        userRepository = UserRepository(database.userDao())
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER_CODE)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER_CODE -> userRepository.getLikedCursor()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER_CODE) {
            sUriMatcher.match(uri) -> userRepository.insertProv(UserEntity.from(values!!))
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_CODE) {
            sUriMatcher.match(uri) -> userRepository.deleteById(uri.lastPathSegment!!.toInt())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}