package com.topanlabs.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.Item
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val listSearch = MutableLiveData<ArrayList<Item>>()
    val listFollowers = MutableLiveData<ArrayList<FollowersModelItem>>()
    val listFollowing = MutableLiveData<ArrayList<FollowingModel>>()
    var repo = MainRepository()
    var jobNya: Job? = null

    fun doSearch(username: String)  {
        jobNya?.cancel()
       jobNya = viewModelScope.launch(Dispatchers.Default) {
            var rawValues: SearchModel? =  repo.searchUser(username)
            listSearch.postValue(rawValues?.items)
        }}

    fun doFollowers(username: String) {
        viewModelScope.launch(Dispatchers.Default) {
            var values = repo.getFollowers(username)
            listFollowers.postValue(values)
        }
    }

    fun doFollowing(username: String) {
        viewModelScope.launch(Dispatchers.Default) {
            var values = repo.getFollowing(username)
            listFollowing.postValue(values)
        }
    }
    fun getWeathers(): LiveData<ArrayList<Item>> {
        return listSearch
    }

    fun getFollowers(): LiveData<ArrayList<FollowersModelItem>> {
        return listFollowers
    }

    fun getFollowing(): LiveData<ArrayList<FollowingModel>> {
        return listFollowing
    }
}