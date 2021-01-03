package com.example.trip_planner_andrid_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.flight_details.*

class SelectSeatActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plane_modal)

        val seatsChecked = ArrayList<CheckBox>()


        var numberOfCheckboxesChecked = 0
        fun seatListener(seat: CheckBox) {
            seat.setOnClickListener { isChecked ->
                if (numberOfCheckboxesChecked >= 2) {
                    if (seat.isChecked) {
                        seatsChecked.add(seat)
                        seatsChecked[0].isChecked = false
                        seatsChecked.removeAt(0)
                    } else {
                        seatsChecked.remove(seat)
                        numberOfCheckboxesChecked--
                    }
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
    }
}