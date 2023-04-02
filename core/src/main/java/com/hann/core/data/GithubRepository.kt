package com.hann.core.data

import com.hann.core.data.source.local.LocalDataSource
import com.hann.core.data.source.remote.RemoteDataSource
import com.hann.core.data.source.remote.network.ApiResponse
import com.hann.core.domain.model.DetailUser
import com.hann.core.domain.model.User
import com.hann.core.domain.repository.IGithubRepository
import com.hann.core.utils.AppExecutors
import com.hann.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class GithubRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors : AppExecutors,
) : IGithubRepository {

    override fun getSearchUser(username: String): Flow<com.hann.core.data.Resource<List<User>>> = flow {
        try {
            emit(com.hann.core.data.Resource.Loading())
            when(val listUser = remoteDataSource.getSearchUser(username).first()){
                is ApiResponse.Success -> {
                    val userList = DataMapper.mapResponsesToEntities(listUser.data)
                    localDataSource.insertUser(userList)
                    val detailUser = DataMapper.mapEntitiesToDomain(userList)
                    emit(com.hann.core.data.Resource.Success(detailUser))
                }
                is ApiResponse.Empty -> emit(com.hann.core.data.Resource.Error("User not found"))
                is ApiResponse.Error -> emit(com.hann.core.data.Resource.Error(listUser.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                com.hann.core.data.Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(com.hann.core.data.Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

    override fun getDetailUser(username: String): Flow<com.hann.core.data.Resource<DetailUser>>  = flow {
        try {
            emit(com.hann.core.data.Resource.Loading())
            when(val listUser = remoteDataSource.getDetailUser(username).first()){
                is ApiResponse.Success -> {
                    val detailUser = DataMapper.mapResponseToDomain(listUser.data)
                    emit(com.hann.core.data.Resource.Success(detailUser))
                }
                is ApiResponse.Empty -> emit(com.hann.core.data.Resource.Error("User not found"))
                is ApiResponse.Error -> emit(com.hann.core.data.Resource.Error(listUser.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                com.hann.core.data.Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(com.hann.core.data.Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

    override fun getFollowing(username: String): Flow<com.hann.core.data.Resource<List<User>>> = flow {
        try {
            emit(com.hann.core.data.Resource.Loading())
            when(val listUser = remoteDataSource.getFollowing(username).first()){
                is ApiResponse.Success -> {
                    val detailUser = DataMapper.mapResponsesToDomainFollowing(listUser.data)
                    emit(com.hann.core.data.Resource.Success(detailUser))
                }
                is ApiResponse.Empty -> emit(com.hann.core.data.Resource.Error("User not found"))
                is ApiResponse.Error -> emit(com.hann.core.data.Resource.Error(listUser.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                com.hann.core.data.Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(com.hann.core.data.Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

    override fun getFollower(username: String): Flow<com.hann.core.data.Resource<List<User>>> = flow {
        try {
            emit(com.hann.core.data.Resource.Loading())
            when(val listUser = remoteDataSource.getFollowers(username).first()){
                is ApiResponse.Success -> {
                    val detailUser = DataMapper.mapResponsesToDomainFollower(listUser.data)
                    emit(com.hann.core.data.Resource.Success(detailUser))
                }
                is ApiResponse.Empty -> emit(com.hann.core.data.Resource.Error("User not found"))
                is ApiResponse.Error -> emit(com.hann.core.data.Resource.Error(listUser.errorMessage))
            }
        }catch (e: HttpException){
            emit(
                com.hann.core.data.Resource.Error(
                    e.localizedMessage ?: "An unexpected Error Occurred"
                )
            )
        }catch (e: IOException){
            emit(com.hann.core.data.Resource.Error("Couldn't reach server. Check your internet server"))
        }
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteDetail(id: String): User {
        return localDataSource.getFavoriteDetail(id).let {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteUser(user: User, state: Boolean) {
        val userEntity = DataMapper.mapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userEntity, state) }
    }
}