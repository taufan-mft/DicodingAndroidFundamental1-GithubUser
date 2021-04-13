package com.topanlabs.githubuser.repository
import com.topanlabs.githubuser.model.FollowersModelItem
import com.topanlabs.githubuser.model.FollowingModel
import com.topanlabs.githubuser.model.SearchModel
import com.topanlabs.githubuser.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface EndpointInterface {
    @Headers("Authorization: token 89d2223f48103eb4498d1990b1930702496845c3")
    @GET("search/users?")
    suspend fun searchUser(@Query("q") username: String) : SearchModel

    @Headers("Authorization: token 89d2223f48103eb4498d1990b1930702496845c3")
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String) : UserModel

    @Headers("Authorization: token 89d2223f48103eb4498d1990b1930702496845c3")
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): ArrayList<FollowersModelItem>

    @Headers("Authorization: token 89d2223f48103eb4498d1990b1930702496845c3")
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): ArrayList<FollowingModel>
}