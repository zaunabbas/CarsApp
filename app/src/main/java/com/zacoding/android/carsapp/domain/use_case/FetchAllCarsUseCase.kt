package com.zacoding.android.carsapp.domain.use_case

import com.zacoding.android.carsapp.data.DataResource
import com.zacoding.android.carsapp.data.remote.responses.CarsResponse
import com.zacoding.android.carsapp.domain.repository.CarsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FetchAllCarsUseCase(
    private val carsRepository: CarsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, DataResource<List<CarsResponse>>>(dispatcher) {

    override fun execute(parameters: Unit): Flow<DataResource<List<CarsResponse>>> {
        return carsRepository.fetchAllCars()
    }
}