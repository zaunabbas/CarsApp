package com.zacoding.android.carsapp.domain

import android.util.Log
import androidx.annotation.WorkerThread
import com.zacoding.android.carsapp.data.remote.Api
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CarsRepository @Inject constructor(
    private val apiService: Api
) {

    @WorkerThread
    fun fetchCars(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {

        // request API network call asynchronously.
        apiService.fetchCars()
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                Log.d("Result", data.toString())
                emit(data)
            }
            // handle the case when the API request is fails.
            // e.g. internal server error.
            .suspendOnError {
                val errorMessage = message()
                Log.d("suspendOnError", errorMessage)
                onError(errorMessage)
            }

    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

}