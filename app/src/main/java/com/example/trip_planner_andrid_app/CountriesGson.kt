package com.example.trip_planner_andrid_app

class CountriesGson {
    data class countriesList(
        val countries: List<countries>
    )
    data class countries(
        val country: String,
        val alfa_2: String,
    )
}
