package com.example.trip_planner_andrid_app
import SeatIdAdapter
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class ProfileFlightsDetails: AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    var listOfSeats: ArrayList<Seat> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modal_confirm_seats)
        val seatClass = intent.getStringExtra("class")
        val departurePlaceIntent = intent.getStringExtra("wylot_z")
        val arrivalPlaceIntent = intent.getStringExtra("przylot_do")
        val priceIntent = intent.getStringExtra("cena")
        val departureDateIntent = intent.getStringExtra("data_wylotu")
        val seatValues = intent.getSerializableExtra("ids") as? SeatValue

        var payButton: Button = findViewById(R.id.payButton)
        val payButtonBackground : FrameLayout = findViewById(R.id.payButtonBackground)
        payButton.visibility = View.INVISIBLE
        payButton.isEnabled = false
        payButtonBackground.visibility = View.INVISIBLE

        for(seat in seatValues?.value!!){
//            println(seat)
//            println(seatClass)
            val seatInfo = Seat()
            seatInfo.id = seat
            seatInfo.seatClass = seatClass
            listOfSeats.add(seatInfo)
        }
        for(seat in listOfSeats){
            println(seat.id)
            println(seat.seatClass)
        }
        mRecyclerView = findViewById(R.id.recyclerview)
        val departurePlace: TextView = findViewById(R.id.originPlace)
        val arrivalPlace: TextView = findViewById(R.id.destinationPlace)
        val price: TextView = findViewById(R.id.price)
        val departureDate: TextView = findViewById(R.id.date)
        findViewById<TextView>(R.id.weekDay).text = getDayName(departureDateIntent);
        departurePlace.text = departurePlaceIntent
        arrivalPlace.text = arrivalPlaceIntent
        price.text = priceIntent
        departureDate.text = departureDateIntent


        var mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mAdapter = SeatIdAdapter(listOfSeats)
        mRecyclerView!!.adapter = mAdapter
    }

    private fun getDayName(flightData: String?): String {
        val weekDayFormat = SimpleDateFormat("EEEE")
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val d: Date = sdf.parse(flightData?.substring(0, 10))
        return weekDayFormat.format(d);
    }
}