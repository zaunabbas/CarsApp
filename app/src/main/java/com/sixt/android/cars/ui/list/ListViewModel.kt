package com.sixt.android.cars.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixt.android.cars.data.CarsData
import com.sixt.android.cars.data.remote.CarsResponse
import com.sixt.android.cars.domain.CarsRepository
import com.sixt.android.cars.util.toCarsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val filterByCarGroup: MutableStateFlow<String?> = MutableStateFlow(null)
    private val allCarsList: MutableStateFlow<ArrayList<CarsData>> = MutableStateFlow(arrayListOf())
    val filteredCarsList: MutableStateFlow<List<CarsData>> = MutableStateFlow(arrayListOf())
    val carsGroupFilterList: MutableStateFlow<List<String>> = MutableStateFlow(arrayListOf())
    val isLoading = MutableStateFlow(false)

    init {
        getAllCars()
    }

    private fun getAllCars() {

        viewModelScope.launch {
            carsRepository.fetchCars(
                onStart = { isLoading.value = true },
                onCompletion = { isLoading.value = false },
                onError = {
                    isLoading.value = false
                    Log.d("Error", it)
                }
            ).collectLatest {

                val allCarsArrayList = arrayListOf<CarsData>()
                it.map { value ->
                    allCarsArrayList.add(
                        value.toCarsData()
                    )
                }
                allCarsList.value = allCarsArrayList

                setupCarsInitialData()

                /*delay(5000)
                updateFilter(carsGroupFilterList.value[1])*/
            }
        }

    }

    private fun setupCarsInitialData() {
        setCarsGroupTagsList()
        filterCarsByGroup()
    }

    private fun filterCarsByGroup() {
        filteredCarsList.value =
            if (filterByCarGroup.value == null)
                allCarsList.value
            else
                allCarsList.value.filter { it.group == filterByCarGroup.value }
    }

    private fun setCarsGroupTagsList() {
        carsGroupFilterList.value = allCarsList.value.groupBy { it.group }.keys.toList()
    }

    fun updateFilter(filterValue: String?) {
        filterByCarGroup.value = filterValue
        filterCarsByGroup()
    }
}
