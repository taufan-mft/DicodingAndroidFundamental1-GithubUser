package com.topanlabs.githubuser.repository
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndpointInterface {
    @GET("search/users?")
    fun searchUser(@Query("username") username: String) : Call<SearchModel>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String) : Call<UserModel>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowersModelItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowingModel>>
}