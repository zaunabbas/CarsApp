package com.zacoding.android.carsapp.ui.maplist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.zacoding.android.carsapp.data.CarsData

class CarsListComparator : DiffUtil.ItemCallback<CarsData>() {
    override fun areItemsTheSame(oldItem: CarsData, newItem: CarsData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CarsData, newItem: CarsData): Boolean {
        return oldItem == newItem
    }
}