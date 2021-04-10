package com.topanlabs.githubuser

import android.app.Application
import com.topanlabs.githubuser.db.UserRoomDatabase
import com.topanlabs.githubuser.repository.UserRepository

class UserApplication : Application() {

    val database by lazy { UserRoomDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
}