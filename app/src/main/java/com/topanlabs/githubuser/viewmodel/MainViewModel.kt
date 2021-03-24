package com.topanlabs.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topanlabs.githubuser.model.Item
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val listSearch = MutableLiveData<ArrayList<Item>>()
    var repo = MainRepository()
    var jobNya: Job? = null

    fun doSearch(username: String)  {
        jobNya?.cancel()
       jobNya = viewModelScope.launch(Dispatchers.Default) {
            var rawValues: SearchModel? =  repo.searchUser(username)
            Log.d("korutin", rawValues.toString())
            listSearch.postValue(rawValues?.items)
        }


    }


    fun getWeathers(): LiveData<ArrayList<Item>> {
        Log.d("VIEWMODEL", listSearch.toString())
        return listSearch
    }
}