package com.bhavanawagh.fetchrewardsapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavanawagh.fetchrewardsapp.api.NetworkResponse
import com.bhavanawagh.fetchrewardsapp.api.NetworkUtils.isNetworkConnected
import com.bhavanawagh.fetchrewardsapp.data.ListResponseItem
import com.bhavanawagh.fetchrewardsapp.repository.ListDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDataViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    repository: ListDataRepository
) : ViewModel() {

    private val _responseData =
        MutableStateFlow<NetworkResponse<List<ListResponseItem?>>>(NetworkResponse.Loading)
    val responseData: StateFlow<NetworkResponse<List<ListResponseItem?>>> =
        _responseData.asStateFlow()

    init {
        try {
            viewModelScope.launch() {
                _responseData.value = NetworkResponse.Loading
                if (isNetworkConnected(applicationContext)) {
                    val itemsFlow: Flow<List<ListResponseItem>> = repository.getProcessedItems()

                    itemsFlow.collect { items ->
                        _responseData.value = NetworkResponse.Success(items)
                        println("Response :  $items")
                    }
                } else {
                    _responseData.value =
                        NetworkResponse.Error("No internet connection. Please check your internet connection")
                }
            }
        } catch (e: Exception) {
            _responseData.value = NetworkResponse.Error(e.message ?: "Something went wrong!")
        }

    }


}