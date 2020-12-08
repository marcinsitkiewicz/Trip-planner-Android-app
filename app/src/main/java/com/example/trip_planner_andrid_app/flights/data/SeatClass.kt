package com.example.trip_planner_andrid_app.flights.data

import com.example.trip_planner_andrid_app.R

data class SeatClass(val image: Int, val name: String)

object Classes {

    private val images = intArrayOf(
        R.drawable.ic_business_class,
        R.drawable.ic_premium_class,
        R.drawable.ic_economy_class,
    )

    private val seats = arrayOf(
        "Klasa Business",
        "Klasa Premium",
        "Klasa Ekonomiczna",
    )

    var list: ArrayList<SeatClass>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices) {

                val imageId = images[i]
                val seatName = seats[i]

                val seats = SeatClass(imageId, seatName)
                field!!.add(seats)
            }

            return field
        }
}