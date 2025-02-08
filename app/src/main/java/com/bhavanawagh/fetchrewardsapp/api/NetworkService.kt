package com.bhavanawagh.fetchrewardsapp.api

import com.bhavanawagh.fetchrewardsapp.data.ListResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("hiring.json")
    suspend fun getListData(): Response<ListResponse>
}