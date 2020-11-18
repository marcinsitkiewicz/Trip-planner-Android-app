package com.example.trip_planner_andrid_app.flights.data

data class SkyscannerQuery(
    val country: String = "PL",
    val currency: String = "PLN",
    val locale: String = "pl-PL",
    val originPlace: String = "WAW-sky",
    val destinationPlace: String = "LHR-sky",
    val outboundPartialDate: String = "2020-12-10",
    val inboundPartialDate: String = "", // optional

    val url: String = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/",
    val rapidApiKey: String = "2fb44953c6msh66777e4d0355d7ep13efbejsn44e928f9677c",
    val rapidApiHost: String = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com"
)


