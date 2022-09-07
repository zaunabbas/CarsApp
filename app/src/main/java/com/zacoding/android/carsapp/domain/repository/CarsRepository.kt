package com.zacoding.android.carsapp.domain.repository

import com.zacoding.android.carsapp.data.DataResource
import com.zacoding.android.carsapp.data.remote.responses.CarsResponse
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    fun fetchAllCars(): Flow<DataResource<List<CarsResponse>>>
}