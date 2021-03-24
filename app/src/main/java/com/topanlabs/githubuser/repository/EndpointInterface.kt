package com.topanlabs.githubuser.repository
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface EndpointInterface {
    @GET("search/users?")
    @Headers("Authorization:token 4019ff375f7cc7dbbd72a8c17d63e7f2e014f47b", "User-Agent:taufan-mft")
    suspend fun searchUser(@Query("q") username: String) : SearchModel

    @GET("users/{username}")
    @Headers("Authorization:token 4019ff375f7cc7dbbd72a8c17d63e7f2e014f47b", "User-Agent:taufan-mft")
    fun getUser(@Path("username") username: String) : Call<UserModel>

    @GET("users/{username}/followers")
    @Headers("Authorization:token 4019ff375f7cc7dbbd72a8c17d63e7f2e014f47b", "User-Agent:taufan-mft")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowersModelItem>>

    @GET("users/{username}/following")
    @Headers("Authorization:token 4019ff375f7cc7dbbd72a8c17d63e7f2e014f47b", "User-Agent:taufan-mft")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowingModel>>
}