package com.zacoding.android.carsapp.ui.maplist.adapter

import com.zacoding.android.carsapp.data.CarsData

interface OnItemClickListener {
    fun onRowClick(
        position: Int,
        carsData: CarsData
    )
}