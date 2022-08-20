package com.sixt.android.cars.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sixt.android.cars.data.CarsData
import com.sixt.android.cars.databinding.ViewholderAlbumItemBinding
import com.sixt.android.cars.ui.list.viewholder.AlbumViewHolder

class AlbumListAdapter(var carsDataList: ArrayList<CarsData>) :
    ListAdapter<CarsData, AlbumViewHolder>(MovieDiffCallback()) {

    private lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val listItemBinding = ViewholderAlbumItemBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = carsDataList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = carsDataList[position]//getItem(position)
        holder.bind(album)
    }

    fun submitRefreshList(itemList: List<CarsData>) {
        this.carsDataList.clear()
        this.carsDataList.addAll(itemList)
        notifyDataSetChanged()
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<CarsData>() {
        override fun areItemsTheSame(oldItem: CarsData, newItem: CarsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarsData, newItem: CarsData): Boolean {
            return oldItem == newItem
        }
    }
}
