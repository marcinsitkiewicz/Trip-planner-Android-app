package com.example.trip_planner_andrid_app

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.search_for_flights_activity.*
import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.trip_planner_andrid_app.flights.FlightsListActivity
import com.google.gson.GsonBuilder
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

        setAutocomplete()

        button2.setOnClickListener{
            val originPlace = wylotZ.text.toString().split("-")[1].trim() + "-sky"
            val destinationPlace = przylotDo.text.toString().split("-")[1].trim() + "-sky"
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

    private fun setAutocomplete() {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")

        val airports =  GsonBuilder().create().fromJson(jsonFileString, AirportGson.airportsList::class.java)
        val airportsList = ArrayList<String>()

        for(a in airports.airports) {
            airportsList.add(a.city + " " + a.name + " - " + a.iata)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line, airportsList
        )
        val textView = findViewById<AutoCompleteTextView>(R.id.wylotZ)
        val textView2 = findViewById<AutoCompleteTextView>(R.id.przylotDo)

        textView.setAdapter(adapter)
        textView2.setAdapter(adapter)
    }

    private fun getJsonDataFromAsset(context: Context, filename: String): String? {
        return context.assets.open(filename).bufferedReader().use { it.readText() }
    }
}


