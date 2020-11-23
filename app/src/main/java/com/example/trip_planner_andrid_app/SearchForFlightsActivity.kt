package com.example.trip_planner_andrid_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.search_for_flights_activity.*
import android.content.Intent
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.trip_planner_andrid_app.flights.FlightsListActivity
import java.text.SimpleDateFormat
import java.util.*

class SearchForFlightsActivity : AppCompatActivity() {

    private val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var outboundDateString : String = ""
    private var inboundDateString : String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_for_flights_activity)

        button2.setOnClickListener{
            val originPlace = wylotZ.text.toString()
            val destinationPlace = przylotDo.text.toString()
            val intent = Intent(this, FlightsListActivity::class.java)
            intent.putExtra("originPlace", originPlace)
            intent.putExtra("destinationPlace", destinationPlace)
            intent.putExtra("outboundDateString", outboundDateString)
            intent.putExtra("inboundDateString", inboundDateString)
            startActivity(intent)
        }


        btn_timePickerWylot.setOnClickListener(View.OnClickListener { val getDate = Calendar.getInstance()
        val datepicker = DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_Alert, DatePickerDialog.OnDateSetListener{datePicker, i, i2, i3 ->

            val selectDate = Calendar.getInstance()
            selectDate.set(Calendar.YEAR, i)
            selectDate.set(Calendar.MONTH, i2)
            selectDate.set(Calendar.DAY_OF_MONTH, i3)
            val date = formatDate.format(selectDate.time)
            outboundDateString = date.toString()
            btn_timePickerWylot.text=date

        }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        })

        btn_timePickerPrzylot.setOnClickListener(View.OnClickListener { val getDate = Calendar.getInstance()
            val datepicker = DatePickerDialog(this, android.R.style.Theme_Material_Light_Dialog_Alert, DatePickerDialog.OnDateSetListener{datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                inboundDateString = date.toString()
                btn_timePickerPrzylot.text=date

            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        })

    }

}


