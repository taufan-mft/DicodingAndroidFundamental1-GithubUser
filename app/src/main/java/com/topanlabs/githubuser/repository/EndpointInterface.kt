package com.topanlabs.githubuser.repository
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndpointInterface {
   // @Headers("Authorization: token ")
    @GET("search/users?")
    suspend fun searchUser(@Query("q") username: String) : SearchModel

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String) : UserModel

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): ArrayList<FollowersModelItem>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): ArrayList<FollowingModel>
}