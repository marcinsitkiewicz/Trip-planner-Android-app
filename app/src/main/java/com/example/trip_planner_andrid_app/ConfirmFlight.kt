package com.example.trip_planner_andrid_app

import SeatIdAdapter
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ConfirmFlight: AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    var listOfSeats: ArrayList<Seat> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modal_confirm_seats)
        val seatClass = intent.getStringExtra("class")
        val seatValues = intent.getSerializableExtra("ids") as? SeatValue
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
        var mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = mLayoutManager
        mAdapter = SeatIdAdapter(listOfSeats)
        mRecyclerView!!.adapter = mAdapter

    }
}