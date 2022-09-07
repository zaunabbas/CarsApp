package com.zacoding.android.carsapp.presentation.maplist.adapter

import com.zacoding.android.carsapp.data.model.CarsData

interface OnItemClickListener {
    fun onRowClick(
        position: Int,
        carsData: CarsData
    )
}