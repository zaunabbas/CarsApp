package com.zacoding.android.carsapp.presentation.maplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zacoding.android.carsapp.data.*
import com.zacoding.android.carsapp.data.model.CarsData
import com.zacoding.android.carsapp.data.repository.CarsRepositoryImpl
import com.zacoding.android.carsapp.domain.use_case.FetchAllCarsUseCase
import com.zacoding.android.carsapp.presentation.base.*
import com.zacoding.android.carsapp.util.toCarsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapListViewModel @Inject constructor(
    private val fetchAllCarsUseCase: FetchAllCarsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<BaseUIState> = MutableStateFlow(EmptyState)
    val uiState: StateFlow<BaseUIState> = _uiState

    private val filterByCarGroup: MutableStateFlow<String?> = MutableStateFlow(null)
    private val allCarsList: MutableStateFlow<ArrayList<CarsData>> = MutableStateFlow(arrayListOf())

    private val _filteredCarsList: MutableStateFlow<List<CarsData>> =
        MutableStateFlow(arrayListOf())
    val filteredCarsList = _filteredCarsList.asStateFlow()

    private val _carsGroupFiltersList: MutableStateFlow<List<String>> =
        MutableStateFlow(arrayListOf())
    val carsGroupFiltersList = _carsGroupFiltersList.asStateFlow()

    init {
        loadAllCars()
    }

    private fun loadAllCars() {

        viewModelScope.launch {
            fetchAllCarsUseCase.invoke(Unit).collectLatest { dataResource ->

                dataResource.onSuccess {

                    val allCarsArrayList = arrayListOf<CarsData>()
                    this.data.map { value ->
                        allCarsArrayList.add(
                            value.toCarsData()
                        )
                    }
                    allCarsList.value = allCarsArrayList

                    setupCarsInitialData()
                    _uiState.value = ContentState
                }.onLoading {
                    _uiState.value = LoadingState
                }.onEmpty {
                    _uiState.value = EmptyState
                }.onError {
                    _uiState.value = ErrorState(this.exception.message!!)
                }


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
