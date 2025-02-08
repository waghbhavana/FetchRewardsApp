package com.bhavanawagh.fetchrewardsapp.repository

import com.bhavanawagh.fetchrewardsapp.api.NetworkService
import com.bhavanawagh.fetchrewardsapp.data.ListResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListDataRepository @Inject constructor(var networkService: NetworkService) {

    private suspend fun getListData(): Flow<List<ListResponseItem>> {
        return flowOf(networkService.getListData().body()?.toList() ?: emptyList())
    }

    suspend fun getProcessedItems(): Flow<List<ListResponseItem>> {
        var data = getListData().map { items ->
            items.filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy<ListResponseItem> { it.listId }.thenBy { it.name })
        }
        return data
    }
}