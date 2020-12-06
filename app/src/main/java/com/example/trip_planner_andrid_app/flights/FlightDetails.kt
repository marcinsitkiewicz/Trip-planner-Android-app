package com.example.trip_planner_andrid_app.flights
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import com.example.trip_planner_andrid_app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.flight_details.*


class FlightDetails: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.flight_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val originCity =  arguments?.getString("originPlace")
        val originIata =  arguments?.getString("originIata")
        val destinationCity =  arguments?.getString("destinationPlace")
        val destinationIata =  arguments?.getString("destinationIata")
        val departureDate =  arguments?.getString("departureDate")
        val price =  arguments?.getString("price")


        var orignFullname: TextView = view.findViewById(R.id.Origin_fullname) as TextView
        var originCode: TextView = view.findViewById(R.id.Origin_code) as TextView
        var destinationFullname: TextView = view.findViewById(R.id.Destination_fullname) as TextView
        var destinationCode: TextView = view.findViewById(R.id.Destination_code) as TextView
        var ticketPrice: TextView = view.findViewById(R.id.TicketPrice_price) as TextView

        orignFullname.text = originCity
        originCode.text = originIata
        destinationFullname.text = destinationCity
        destinationCode.text = destinationIata
        ticketPrice.text = price + " PLN"


        var numberOfCheckboxesChecked = 1
        seat1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && numberOfCheckboxesChecked >= 2) {
                seat1.isChecked = false
            } else {
                if (isChecked) {
                    numberOfCheckboxesChecked++
                } else {
                    numberOfCheckboxesChecked--
                }
            }
        }


    }

}
