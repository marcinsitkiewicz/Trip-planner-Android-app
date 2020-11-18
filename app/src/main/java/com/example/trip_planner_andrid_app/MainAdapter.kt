package com.example.trip_planner_andrid_app

import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trip_planner_andrid_app.flights.data.SkyscannerResults
import kotlinx.android.synthetic.main.flight_row.view.*

class MainAdapter(private val searchFeed: SkyscannerResults.SearchFeed): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int{
        return searchFeed.Quotes.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.flight_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    // lot: 1 2 3 4 5 6 7 8
    // prz: 1 2 1 2 1 2 1 2

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val flight = searchFeed.Quotes.get(position)
        val place1 = searchFeed.Places.get(1)
        val place2 = searchFeed.Places.get(0)

        val direct : String
        direct = if (flight.Direct)
            "Przesiadki"
        else "Brak przesiadek"

        holder.view.data_wylotu.text = flight.OutboundLeg.DepartureDate
        holder.view.data_przylotu.text = flight.OutboundLeg.DepartureDate
        holder.view.cena.text = flight.MinPrice.toString()
        holder.view.wylot_z.text = place1.CityName
        holder.view.przylot_do.text = place2.CityName
        holder.view.przesiadki.text = direct

        searchFeed.Carriers.forEach {
            if (it.CarrierId == flight.OutboundLeg.CarrierIds.get(0)) {
                holder.view.przewoznik.text = it.Name
            }
        }
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}