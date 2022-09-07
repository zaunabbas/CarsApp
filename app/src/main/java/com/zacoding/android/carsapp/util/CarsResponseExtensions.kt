package com.zacoding.android.carsapp.util

import com.zacoding.android.carsapp.data.model.CarsData
import com.zacoding.android.carsapp.data.remote.responses.CarsResponse


fun CarsResponse.toCarsData() = CarsData(
    id = id,
    modelName = modelName,
    name = name,
    make = make,
    group = group,
    color = color.replace("_", " ").replaceFirstChar { it.uppercase() },
    licensePlate = licensePlate,
    latitude = latitude,
    longitude = longitude,
    carImageUrl = carImageUrl
)