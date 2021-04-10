package com.topanlabs.githubuser.repository

import com.google.gson.GsonBuilder
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainRepository {
    val BASE_URL = "https://api.github.com"

    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

   val githubApi: EndpointInterface = retrofit.create(EndpointInterface::class.java)

    suspend fun searchUser(username: String): SearchModel? {
        var search: SearchModel? = null
        try {

            val call: SearchModel = githubApi.searchUser(username)
            return call
        } catch(e: Exception) {
            return null
        }
    }

    suspend fun getUser(username: String): UserModel? {
        var user: UserModel? = null
        try{
            user = githubApi.getUser(username)
        } catch(e: Exception){
            return null
        }
        return user
    }

    suspend fun getFollowers(username: String): ArrayList<FollowersModelItem>? {
        var followers: ArrayList<FollowersModelItem>? = ArrayList()
        try {
            followers = githubApi.getUserFollowers(username)
        } catch (e: Exception) {

    }
        return followers
    }
    suspend fun getFollowing(username: String): ArrayList<FollowingModel>? {
        var following: ArrayList<FollowingModel>? = ArrayList()
        try {
           following = githubApi.getUserFollowing(username)
        } catch(e: Exception) {

        }
        return following
    }
}