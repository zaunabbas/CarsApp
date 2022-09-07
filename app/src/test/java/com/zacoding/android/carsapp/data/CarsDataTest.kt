package com.zacoding.android.carsapp.data

import com.zacoding.android.carsapp.data.remote.responses.CarsResponse
import com.zacoding.android.carsapp.util.toCarsData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarsDataTest {

    private lateinit var carsResponseMockList: ArrayList<CarsResponse>

    @Before
    fun setupMockData() {
        carsResponseMockList = arrayListOf(
            CarsResponse(
                "WMWSW31030T222518",
                "mini",
                "MINI",
                "Vanessa",
                "BMW",
                "MINI",
                "midnight_black",
                "MINI",
                "D",
                0.7,
                "M",
                "M-VO0259",
                48.134557,
                11.576921,
                "REGULAR",
                "https://cdn.sixt.io/codingtask/images/mini.png",
            ),
            CarsResponse(
                "WMWSW31030T222519",
                "mini",
                "MINI",
                "Vanessa",
                "BMW",
                "BMW",
                "midnight_black",
                "MINI",
                "D",
                0.7,
                "M",
                "M-VO0258",
                48.134558,
                11.576922,
                "REGULAR",
                "https://cdn.sixt.io/codingtask/images/mini.png",
            )
        )

    }

    @Test
    fun `maps CarResponse to CarData using extension function`() {
        val carData = carsResponseMockList.first().toCarsData()
        assertEquals(carData.id, "WMWSW31030T222518")
    }

    @Test
    fun `cars group tags length equals 2`() {
        val groupTagsList = carsResponseMockList.groupBy { it.group }.keys

        assertEquals(groupTagsList.size, 2)
    }
}