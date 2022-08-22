package com.zacoding.android.carsapp.ui.maplist.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zacoding.android.carsapp.R
import com.zacoding.android.carsapp.data.CarsData
import com.zacoding.android.carsapp.databinding.ViewholderCarsItemBinding
import com.zacoding.android.carsapp.ui.maplist.adapter.OnItemClickListener

class CarsViewHolder(private val binding: ViewholderCarsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        carsData: CarsData,
        listener: OnItemClickListener?
    ) {
        binding.tvOwnerName.text = carsData.name
        binding.tvLp.text = carsData.licensePlate
        binding.tvCarDetail.text = String.format(
            binding.root.context.getString(R.string.car_details_format),
            carsData.make, carsData.modelName, carsData.color
        )

        Glide.with(binding.root.context)
            .load(carsData.carImageUrl)
            .into(binding.ivCar)

        itemView.setOnClickListener {
            listener.let {
                listener?.onRowClick(adapterPosition, carsData)
            }
        }
    }
}