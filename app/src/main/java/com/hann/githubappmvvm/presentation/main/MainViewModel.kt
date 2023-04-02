package com.hann.githubappmvvm.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.core.data.Resource
import com.hann.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {

    private val _state = MutableLiveData<UserListState>()
    val state : LiveData<UserListState> = _state

    private val _isLoadingSplash = MutableStateFlow(true)
    val isLoadingSplash = _isLoadingSplash.asStateFlow()


    init {
        viewModelScope.launch {
            delay(3000)
            _isLoadingSplash.value = false
        }
        getUsers("a")
    }

    fun getUsers(username: String){
        githubUseCase.getSearchUser(username).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = UserListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = UserListState(error = result.message ?: "An unexpected Error occured")
                }
                is Resource.Success -> {
                    _state.value = UserListState(users = result.data ?: emptyList())
                }

            }
        }.launchIn(viewModelScope)
    }

}