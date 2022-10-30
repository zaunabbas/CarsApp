package com.zacoding.android.carsapp.data.remote

import com.skydoves.sandwich.ApiResponse
import com.zacoding.android.carsapp.data.remote.responses.CarsResponse
import retrofit2.http.GET

interface Api {

    @GET("cars")
    suspend fun fetchCars(
    ): List<CarsResponse>
}
