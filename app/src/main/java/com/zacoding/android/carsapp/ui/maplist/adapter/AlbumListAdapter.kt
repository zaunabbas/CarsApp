package com.zacoding.android.carsapp.ui.maplist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.zacoding.android.carsapp.data.CarsData
import com.zacoding.android.carsapp.databinding.ViewholderCarsItemBinding
import com.zacoding.android.carsapp.ui.maplist.viewholder.CarsViewHolder

class AlbumListAdapter(var carsDataList: ArrayList<CarsData>) :
    ListAdapter<CarsData, CarsViewHolder>(CarsListComparator()) {

    var listener: OnItemClickListener? = null

    private lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val listItemBinding = ViewholderCarsItemBinding.inflate(layoutInflater, parent, false)
        return CarsViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = carsDataList.size

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val carData = carsDataList[position]//getItem(position)
        holder.bind(carData,listener)
    }

    fun submitRefreshList(itemList: List<CarsData>) {
        this.carsDataList.clear()
        this.carsDataList.addAll(itemList)
        notifyDataSetChanged()
    }
}
