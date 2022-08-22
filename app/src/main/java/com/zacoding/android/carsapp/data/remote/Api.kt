package com.zacoding.android.carsapp.data.remote

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface Api {

    @GET("cars")
    suspend fun fetchCars(
    ): ApiResponse<ArrayList<CarsResponse>>
}
