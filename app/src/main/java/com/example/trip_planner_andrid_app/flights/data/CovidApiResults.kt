package com.example.trip_planner_andrid_app.flights.data

class CovidApiResults {

    data class CountryData(
        val updated: Long,
        val country: String,
        val cases: Int,
        val todayCases: Int,
        val deaths: Int,
        val todayDeaths: Int,
        val recovered: Int,
        val todayRecovered: Int,
        val active: Int,
        val critical: Int,
        val casesPerOneMillion: Double,
        val deathsPerOneMillion: Double,
        val tests: Int,
        val testsPerOneMillion: Double,
        val population: Int,
        val continent: String,
        val oneCasePerPeople: Double,
        val oneDeathPerPeople: Double,
        val oneTestPerPeople: Double,
        val activePerOneMillion: Double,
        val recoveredPerOneMillion: Double,
        val criticalPerOneMillion: Double,
        val countryInfo: countryInfo
    )

    data class countryInfo(
        val _id: Int,
        val iso2: String,
        val iso3: String,
        val lat: Double,
        val long: Double,
        val flag: String
    )
}