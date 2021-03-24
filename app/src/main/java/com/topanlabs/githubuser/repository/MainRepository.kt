package com.topanlabs.githubuser.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainRepository(val username: String = "") {
    val BASE_TAG = "MAINREPO"
    val BASE_URL = "https://api.github.com"

    val gson= GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

   val githubApi: EndpointInterface = retrofit.create(EndpointInterface::class.java)

    suspend fun searchUser(username: String): SearchModel? {
        Log.d(BASE_TAG, "LAGI NYARI")
        var search: SearchModel? = null
        try {

            val call: SearchModel = githubApi.searchUser(username)
            return call
        } catch(e: Exception) {
            return null
        }
    }

    fun getUser(): UserModel? {
        var user: UserModel? = null
        val call = githubApi.getUser(username)
        call.enqueue(object: Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                user = response.body()

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d(BASE_TAG, "Error get user")
            }
        })
        return user
    }

    fun getFollowers(): List<FollowersModelItem>? {
        var followers: List<FollowersModelItem>? = listOf()
        val call = githubApi.getUserFollowers(username)
        call.enqueue(object: Callback<List<FollowersModelItem>> {
            override fun onResponse(call: Call<List<FollowersModelItem>>, response: Response<List<FollowersModelItem>>) {
                followers = response.body()

            }

            override fun onFailure(call: Call<List<FollowersModelItem>>, t: Throwable) {
                Log.d(BASE_TAG, "Error get user")
            }
        })
        return followers
    }
    fun getFollowing(): List<FollowingModel>? {
        var following: List<FollowingModel>? = listOf()
        val call = githubApi.getUserFollowing(username)
        call.enqueue(object: Callback<List<FollowingModel>> {
            override fun onResponse(call: Call<List<FollowingModel>>, response: Response<List<FollowingModel>>) {
                following = response.body()

            }

            override fun onFailure(call: Call<List<FollowingModel>>, t: Throwable) {
                Log.d(BASE_TAG, "Error get user")
            }
        })
        return following
    }
}