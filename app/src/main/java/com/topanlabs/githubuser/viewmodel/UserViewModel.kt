package com.topanlabs.githubuser.viewmodel

import android.database.Cursor
import androidx.lifecycle.*
import com.topanlabs.githubuser.db.UserEntity
import com.topanlabs.githubuser.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val allLikedUsers: LiveData<List<UserEntity>> = repository.allLikedUsers.asLiveData()

    fun insert(userEntity: UserEntity) = viewModelScope.launch {
        repository.insert(userEntity)
    }

    fun delete(userEntity: UserEntity) = viewModelScope.launch {
        repository.delete(userEntity)
    }

    suspend fun getUser(username: String): UserEntity {
        return repository.getUser(username)
    }

    suspend fun isLiked(username: String): Boolean {
        val count: Int = repository.searchUser(username)
        return count > 0
    }

    suspend fun getCursor(): Cursor {
        return repository.getLikedCursor()
    }
}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}