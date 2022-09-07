package com.zacoding.android.carsapp.data.remote

import com.skydoves.sandwich.ApiResponse
import com.zacoding.android.carsapp.data.remote.responses.CarsResponse
import retrofit2.http.GET

interface Api {

    @GET("4ae5b693-cbac-457b-bfe8-6bdfa5500177")
    suspend fun fetchCars(
    ): List<CarsResponse>
}
