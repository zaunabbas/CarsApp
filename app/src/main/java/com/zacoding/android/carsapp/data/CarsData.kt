package com.zacoding.android.carsapp.data

data class CarsData(
    val id: String,
    val modelName: String,
    val name: String,
    val make: String,
    val group: String,
    val color: String,
    val licensePlate: String,
    val latitude: Double,
    val longitude: Double,
    val carImageUrl: String,
)
