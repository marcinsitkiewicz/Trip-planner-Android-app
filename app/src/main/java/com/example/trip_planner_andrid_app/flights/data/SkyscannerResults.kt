package com.example.trip_planner_andrid_app.flights.data

class SkyscannerResults {

    data class SearchFeed(
        val Quotes: List<Quote>,
        val Places: List<Place>,
        val Carriers: List<Carrier>,
        val Currencies: List<Currency>
    )

    data class Quote(
        val QuoteId: Int,
        val MinPrice: Int,
        val Direct: Boolean,
        val QuoteDateTime: String,
        val OutboundLeg: Outbound,
        val InboundLeg: Inbound
    ) {
        data class Outbound(
            val CarrierIds: Array<Int>,
            val OriginId: Int,
            val DestinationId: Int,
            val DepartureDate: String
        )
        data class Inbound(
            val CarrierIds: Array<Int>,
            val OriginId: Int,
            val DestinationId: Int,
            val DepartureDate: String
        )
    }

    data class Carrier(
        val CarrierId: Int,
        val Name: String
    )

    data class Place(
        val Name: String,
        val Type: String,
        val PlaceId: Int,
        val IataCode: String,
        val SkyscannerCode: String,
        val CityName: String,
        val CityId: String,
        val CountryName: String
    )

    data class Currency(
        val Code: String,
        val Symbol: String,
        val ThousandsSeparator: Char,
        val DecimalSeparator: Char,
        val SymbolOnLeft: Boolean,
        val SpaceBetweenAmountAndSymbol: Boolean,
        val RoundingCoefficient: Int,
        val DecimalDigits: Int
    )
}