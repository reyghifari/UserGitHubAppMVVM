package com.hann.core.data.source.remote

import android.util.Log
import com.hann.core.data.source.remote.network.ApiResponse
import com.hann.core.data.source.remote.network.ApiService
import com.hann.core.data.source.remote.response.DetailResponse
import com.hann.core.data.source.remote.response.FollowersResponseItem
import com.hann.core.data.source.remote.response.FollowingResponseItem
import com.hann.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    suspend fun getSearchUser(username : String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchUser(username)
                val dataArray = response.items
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.items))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username : String): Flow<ApiResponse<DetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetailUser(username)
                emit(ApiResponse.Success(response))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getFollowers(username : String): Flow<ApiResponse<List<FollowersResponseItem>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowing(username : String): Flow<ApiResponse<List<FollowingResponseItem>>> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("Remote Data Source", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }



}