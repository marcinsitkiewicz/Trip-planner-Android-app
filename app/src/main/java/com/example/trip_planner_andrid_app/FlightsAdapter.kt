package com.example.trip_planner_andrid_app

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.trip_planner_andrid_app.flights.data.SkyscannerResults
import android.content.Context
import kotlinx.android.synthetic.main.flight_row.view.*


class FlightsAdapter(private val searchFeed: SkyscannerResults.SearchFeed, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int{
        return searchFeed.Quotes.count()
    }

    override fun getItemViewType(position: Int): Int {
        val flight = searchFeed.Quotes[position]

        return if ((flight.InboundLeg == null)) {
            TYPE_ONE_WAY
        } else {
            TYPE_TWO_WAY
        }
    }

    internal class OneWayHolder(itemView: View) : ViewHolder(itemView) {
        private var outboundDate: TextView = itemView.findViewById(R.id.data_wylotu) as TextView
        private var arrivalDate: TextView = itemView.findViewById(R.id.data_przylotu) as TextView
        private var price: TextView = itemView.findViewById(R.id.cena) as TextView
        private var outboundPlace: TextView = itemView.findViewById(R.id.wylot_z) as TextView
        private var destinationPlace: TextView = itemView.findViewById(R.id.przylot_do) as TextView
        private var isDirect: TextView = itemView.findViewById(R.id.przesiadki) as TextView
        private var carrier: TextView = itemView.findViewById(R.id.przewoznik) as TextView

        fun setOneWayFlight(searchFeed: SkyscannerResults.SearchFeed) {
            val flight = searchFeed.Quotes[adapterPosition]
            val place1 : SkyscannerResults.Place
            val place2 : SkyscannerResults.Place

            if (flight.OutboundLeg.OriginId == searchFeed.Places[0].PlaceId) {
                place1 = searchFeed.Places[0]
                place2 = searchFeed.Places[1]
            } else {
                place1 = searchFeed.Places[1]
                place2 = searchFeed.Places[0]
            }

            val direct : String
            direct = if (flight.Direct)
                "Przesiadki"
            else "Brak przesiadek"

            outboundDate.text = flight.OutboundLeg.DepartureDate
            outboundPlace.text = place1.CityName
            arrivalDate.text = flight.OutboundLeg.DepartureDate
            price.text = flight.MinPrice.toString()
            destinationPlace.text = place2.CityName
            isDirect.text = direct

            searchFeed.Carriers.forEach {
                if (it.CarrierId == flight.OutboundLeg.CarrierIds[0]) {
                    carrier.text = it.Name
                }
            }
        }
    }

    internal class TwoWayHolder(itemView: View) : ViewHolder(itemView) {
        private var outboundDate: TextView = itemView.findViewById(R.id.data_wylotu) as TextView
        private var outboundPlace: TextView = itemView.findViewById(R.id.wylot_z) as TextView
        private var arrivalPlace: TextView = itemView.findViewById(R.id.przylot_do) as TextView
        private var arrivalDate: TextView = itemView.findViewById(R.id.data_przylotu) as TextView

        private var inboundDate: TextView = itemView.findViewById(R.id.data_wylotu2) as TextView
        private var inboundPlace: TextView = itemView.findViewById(R.id.wylot_z2) as TextView
        private var arrivalDate2: TextView = itemView.findViewById(R.id.data_przylotu2) as TextView
        private var arrivalPlace2: TextView = itemView.findViewById(R.id.przylot_do2) as TextView

        private var price: TextView = itemView.findViewById(R.id.cena) as TextView

        private var isDirect: TextView = itemView.findViewById(R.id.przesiadki) as TextView
        private var isDirect2: TextView = itemView.findViewById(R.id.przesiadki2) as TextView
        private var carrierOutbound: TextView = itemView.findViewById(R.id.przewoznik) as TextView
        private var carrierInbound: TextView = itemView.findViewById(R.id.przewoznik2) as TextView

        fun setTwoWayFlight(searchFeed: SkyscannerResults.SearchFeed) {
            val flight = searchFeed.Quotes[adapterPosition]
            val place1 = searchFeed.Places[1]
            val place2 = searchFeed.Places[0]

            val direct : String
            direct = if (flight.Direct)
                "Przesiadki"
            else "Brak przesiadek"

            outboundDate.text = flight.OutboundLeg.DepartureDate
            outboundPlace.text = place1.CityName
            arrivalDate.text = flight.OutboundLeg.DepartureDate
            arrivalPlace.text = place2.CityName

            inboundDate.text = flight.InboundLeg.DepartureDate
            inboundPlace.text = place2.CityName
            arrivalDate2.text = flight.InboundLeg.DepartureDate
            arrivalPlace2.text = place1.CityName

            price.text = flight.MinPrice.toString()

            isDirect.text = direct
            isDirect2.text = direct

            searchFeed.Carriers.forEach {
                if (it.CarrierId == flight.OutboundLeg.CarrierIds[0]) {
                    carrierOutbound.text = it.Name
                }
            }

            searchFeed.Carriers.forEach {
                if (it.CarrierId == flight.InboundLeg.CarrierIds[0]) {
                    carrierInbound.text = it.Name
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return if (viewType == TYPE_ONE_WAY) {
            view = LayoutInflater.from(context).inflate(R.layout.flight_row, viewGroup, false)
            OneWayHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.flight_row_two_way, viewGroup, false)
            TwoWayHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ONE_WAY) {
            (viewHolder as OneWayHolder).setOneWayFlight(searchFeed)
        } else {
            (viewHolder as TwoWayHolder).setTwoWayFlight(searchFeed)
        }
    }

    companion object {
        private const val TYPE_ONE_WAY = 0
        private const val TYPE_TWO_WAY = 1
    }
}