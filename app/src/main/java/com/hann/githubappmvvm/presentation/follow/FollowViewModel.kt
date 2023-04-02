package com.hann.githubappmvvm.presentation.follow

import androidx.lifecycle.*
import com.hann.core.data.Resource
import com.hann.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FollowViewModel(
    private val githubUseCase: GithubUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FollowListState>()
    val state : LiveData<FollowListState> = _state


    fun getFollower(username: String){
        githubUseCase.getFollower(username).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = FollowListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = FollowListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = FollowListState(users = result.data ?: emptyList())
                }

            }
        }.launchIn(viewModelScope)
    }

    fun getFollowing(username: String){
        githubUseCase.getFollowing(username).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = FollowListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = FollowListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = FollowListState(users = result.data ?: emptyList())
                }

            }
        }.launchIn(viewModelScope)
    }

}