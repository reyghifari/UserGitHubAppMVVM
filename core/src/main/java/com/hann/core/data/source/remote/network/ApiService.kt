package com.hann.core.data.source.remote.network


import com.hann.core.data.source.remote.response.DetailResponse
import com.hann.core.data.source.remote.response.FollowersResponse
import com.hann.core.data.source.remote.response.FollowingResponse
import com.hann.core.data.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") username: String,
    ) : SearchResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : DetailResponse


    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ) : FollowersResponse


    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ) : FollowingResponse

}