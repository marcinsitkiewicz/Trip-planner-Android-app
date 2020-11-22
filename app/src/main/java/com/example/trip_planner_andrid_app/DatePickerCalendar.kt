package com.example.trip_planner_andrid_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.search_for_flights_activity.*
import android.content.Intent
import android.widget.DatePicker
import com.example.trip_planner_andrid_app.flights.FlightsListActivity
import java.util.*

class DatePickerCalendar:  DatePickerDialog.OnDateSetListener {

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    fun getDateTimeCallendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }


    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year


        getDateTimeCallendar()
//        btn_timePickerWylot.text = "$departureDay.$departureMonth.$departureYear"}
}
}


