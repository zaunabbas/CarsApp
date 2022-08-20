package com.sixt.android.cars.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sixt.android.cars.data.CarsData
import com.sixt.android.cars.databinding.ViewholderAlbumItemBinding

class AlbumViewHolder(private val binding: ViewholderAlbumItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        carsData: CarsData
    ) {
        binding.tvId.text = carsData.id
        binding.tvTitle.text = carsData.name
    }
}