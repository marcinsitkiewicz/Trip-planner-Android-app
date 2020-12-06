package com.example.trip_planner_andrid_app.flights
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridView
import android.widget.TextView
import com.example.trip_planner_andrid_app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.flight_details.*


class FlightDetails: BottomSheetDialogFragment() {

    val seatsChecked = ArrayList<CheckBox>()

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
        ticketPrice.text = "$price PLN"


        var numberOfCheckboxesChecked = 0
        fun seatListener(seat : CheckBox){
            seat.setOnClickListener { isChecked ->
                if (numberOfCheckboxesChecked >= 2) {
                    if(seat.isChecked){
                        seatsChecked.add(seat)
                        seatsChecked[0].isChecked = false
                        seatsChecked.removeAt(0)
                    }else{
                        seatsChecked.remove(seat)
                        numberOfCheckboxesChecked--}
                } else {
                    if (seat.isChecked) {
                        numberOfCheckboxesChecked++
                        seatsChecked.add(seat)
                    } else {
                        numberOfCheckboxesChecked--
                        seatsChecked.remove(seat)
                    }
                }
            }
        }

        seatListener(seat1)
        seatListener(seat2)
        seatListener(seat3)
        seatListener(seat4)

    }

}
