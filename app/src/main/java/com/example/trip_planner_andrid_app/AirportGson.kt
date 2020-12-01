package com.example.trip_planner_andrid_app
class AirportGson
{
    data class airportsList(
        val airports: List<airports>
    )
    data class airports(
        val city: String,
        val country: String,
        val iata: String,
        val name: String
    )

}
