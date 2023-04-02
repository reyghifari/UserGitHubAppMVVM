package com.hann.core.domain.usecase

import com.hann.core.data.Resource
import com.hann.core.domain.model.DetailUser
import com.hann.core.domain.model.User
import com.hann.core.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val iGithubRepository: IGithubRepository) : GithubUseCase {

    override fun getSearchUser(username: String): Flow<Resource<List<User>>> {
       return iGithubRepository.getSearchUser(username)
    }

    override fun getDetailUser(username: String): Flow<Resource<DetailUser>> {
        return iGithubRepository.getDetailUser(username)
    }

    override fun getFollowing(username: String): Flow<Resource<List<User>>> {
        return iGithubRepository.getFollowing(username)
    }

    override fun getFollower(username: String): Flow<Resource<List<User>>> {
        return iGithubRepository.getFollower(username)
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return iGithubRepository.getFavoriteUser()
    }

    override fun getFavoriteDetail(id: String): User {
        return iGithubRepository.getFavoriteDetail(id)
    }

    override fun setFavoriteUser(user: User, state: Boolean) {
        return iGithubRepository.setFavoriteUser(user, state)
    }

}