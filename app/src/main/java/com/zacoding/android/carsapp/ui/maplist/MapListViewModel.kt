package com.zacoding.android.carsapp.ui.maplist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacoding.android.carsapp.data.CarsData
import com.zacoding.android.carsapp.domain.CarsRepository
import com.zacoding.android.carsapp.util.toCarsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapListViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val filterByCarGroup: MutableStateFlow<String?> = MutableStateFlow(null)
    private val allCarsList: MutableStateFlow<ArrayList<CarsData>> = MutableStateFlow(arrayListOf())

    private val _filteredCarsList: MutableStateFlow<List<CarsData>> = MutableStateFlow(arrayListOf())
    val filteredCarsList = _filteredCarsList.asStateFlow()

    private val _carsGroupFiltersList: MutableStateFlow<List<String>> = MutableStateFlow(arrayListOf())
    val carsGroupFiltersList = _carsGroupFiltersList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllCars()
    }

    private fun getAllCars() {

        viewModelScope.launch {
            carsRepository.fetchCars(
                onStart = { _isLoading.value = true },
                onCompletion = { _isLoading.value = false },
                onError = {
                    _isLoading.value = false
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
            }
        }

    }

    private fun setupCarsInitialData() {
        setCarsGroupTagsList()
        filterCarsByGroup()
    }

    private fun filterCarsByGroup() {
        _filteredCarsList.value =
            if (filterByCarGroup.value == null)
                allCarsList.value
            else
                allCarsList.value.filter { it.group == filterByCarGroup.value }
    }

    private fun setCarsGroupTagsList() {
        _carsGroupFiltersList.value = allCarsList.value.groupBy { it.group }.keys.toList()
    }

    fun updateFilter(filterValue: String?) {
        filterByCarGroup.value = filterValue
        filterCarsByGroup()
    }
}
