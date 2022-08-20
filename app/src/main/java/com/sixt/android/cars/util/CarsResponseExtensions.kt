package com.sixt.android.cars.util

import com.sixt.android.cars.data.CarsData
import com.sixt.android.cars.data.remote.CarsResponse


fun CarsResponse.toCarsData() = CarsData(
    id = id,
    modelName = modelName,
    name = name,
    make = make,
    group = group,
    color = color,
    licensePlate = licensePlate,
    latitude = latitude,
    longitude = longitude,
    carImageUrl = carImageUrl
)