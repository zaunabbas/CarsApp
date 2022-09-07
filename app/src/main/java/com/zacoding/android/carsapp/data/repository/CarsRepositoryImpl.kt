package com.zacoding.android.carsapp.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.zacoding.android.carsapp.data.DataResource
import com.zacoding.android.carsapp.data.callApi
import com.zacoding.android.carsapp.data.remote.Api
import com.zacoding.android.carsapp.domain.repository.CarsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val apiService: Api
) : CarsRepository {

    override fun fetchAllCars() = flow {
        emit(DataResource.Loading)
        val result = callApi {
            apiService.fetchCars()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

}