package com.example.trip_planner_andrid_app.flights.data

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trip_planner_andrid_app.R
import com.example.trip_planner_andrid_app.SelectSeatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.flight_details_new.*
import kotlinx.android.synthetic.main.spinner_item.*
import kotlinx.android.synthetic.main.spinner_item.view.*
import java.io.Serializable
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class NewFlightDetails: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val inboundDateString = bundle?.getString("inboundDateString")
        println("inboundDateString ->details --> $inboundDateString")

        var originIataTextView: TextView? = null
        var destinationIataTextView: TextView? = null
        var outboundTime: TextView? = null
        var outboundTimeTwoWay: TextView? = null
        var inboundTimeTwoWay: TextView? = null

        var destinationIataTextViewTwoWays: TextView? = null
        var originIataTextViewTwoWays: TextView? = null

        if(inboundDateString.equals(null)) {
            setContentView(R.layout.flight_details_new)

            originIataTextView = this.findViewById(R.id.origin_iata) as TextView
            destinationIataTextView = this.findViewById(R.id.destination_iata) as TextView
            outboundTime = this.findViewById(R.id.outbound_time) as TextView

        }else{
            setContentView(R.layout.flight_details_two_ways)

            originIataTextView = this.findViewById(R.id.origin_iata_two_ways) as TextView
            destinationIataTextView = this.findViewById(R.id.destination_iata_two_ways) as TextView
            outboundTimeTwoWay = this.findViewById(R.id.outbound_time_two_ways) as TextView
            inboundTimeTwoWay = this.findViewById(R.id.inbound_time_two_ways) as TextView

            destinationIataTextViewTwoWays = this.findViewById(R.id.destination_iatatwo) as TextView
            originIataTextViewTwoWays = this.findViewById(R.id.origin_iatatwo) as TextView

        }

        val originIata = bundle?.getString("originIata")
        val destinationIata = bundle?.getString("destinationIata")
        val time = bundle?.getString("time")

//        val originIataTextView: TextView = this.findViewById(R.id.origin_iata) as TextView
//        val destinationIataTextView: TextView = this.findViewById(R.id.destination_iata) as TextView
//        val outboundTime: TextView = this.findViewById(R.id.outbound_time) as TextView
//
        val numberOfAdults: TextView = this.findViewById(R.id.numberOf_seatsAdults) as TextView
        val numberOfKids: TextView = this.findViewById(R.id.numberOf_seatsKids) as TextView

        val intent = Intent(this, SelectSeatActivity::class.java)
        if(!inboundDateString.equals(null)) {
            intent.putExtra("inboundDateString", inboundDateString)
            outboundTimeTwoWay?.text = time
            inboundTimeTwoWay?.text = time
            destinationIataTextViewTwoWays?.text = originIata
            originIataTextViewTwoWays?.text = destinationIata
        }
        originIataTextView?.text = originIata
        destinationIataTextView?.text = destinationIata
        outboundTime?.text = time

        println("time----->$time")

        setupCustomSpinner(intent)

        select_numberOf_Adults.setOnClickListener { showAlertDialogAdults() }
        select_numberOf_Kids.setOnClickListener { showAlertDialogKids() }
        seatsButton.setOnClickListener {
            val pickedValueAdults: Int = Integer.parseInt(numberOfAdults?.text.toString())
            val pickedValueKids: Int = Integer.parseInt(numberOfKids?.text.toString())
            println("\n\n\n" + pickedValueAdults + "\n\n" + pickedValueKids + "\n\n\n")
            if((pickedValueAdults + pickedValueKids) == 0){
                Toast.makeText(this@NewFlightDetails,
                        "Musisz wybrać co najmniej 1 siedzenie",
                            Toast.LENGTH_SHORT).show()

            }else {
                intent.putExtra("NumberOfAdults", pickedValueAdults)
                intent.putExtra("NumberOfKids", pickedValueKids)
                setIntent(intent)
                startActivity(intent)
            }
        }

        val reservedEconomySeats = generateRandomNumberOfSeats(20, 58)
        val reservedBusinessSeats = generateRandomNumberOfSeats(6, 16)
        val reservedPremiumSeats = generateRandomNumberOfSeats(14, 24)

        val economyClass = ClassSeatList(reservedEconomySeats)
        val businessClass = ClassSeatList(reservedBusinessSeats)
        val premiumClass = ClassSeatList(reservedPremiumSeats)

        intent.putExtra("reservedEconomySeats", economyClass)
        intent.putExtra("reservedBusinessSeats", businessClass)
        intent.putExtra("reservedPremiumSeats", premiumClass)

        if(!inboundDateString.equals(null)){
            println("inboundDateString intent -->$inboundDateString")
            val reservedEconomySeatsTwoWay = generateRandomNumberOfSeats(20, 58)
            val reservedBusinessSeatsTwoWay = generateRandomNumberOfSeats(6, 16)
            val reservedPremiumSeatsTwoWay= generateRandomNumberOfSeats(14, 24)

            val economyClassTwoWay = ClassSeatList(reservedEconomySeatsTwoWay)
            val businessClassTwoWay = ClassSeatList(reservedBusinessSeatsTwoWay)
            val premiumClassTwoWay = ClassSeatList(reservedPremiumSeatsTwoWay)

            intent.putExtra("reservedEconomySeatsTwoWay", economyClassTwoWay)
            intent.putExtra("reservedBusinessSeatsTwoWay", businessClassTwoWay)
            intent.putExtra("reservedPremiumSeatsTwoWay", premiumClassTwoWay)
        }

        val departureDate = bundle?.getString("departureDate").toString()
        val price = bundle?.getString("price").toString()
        val originPlace = bundle?.getString("originPlace").toString()
        val destinationPlace = bundle?.getString("destinationPlace").toString()
        val carrier = bundle?.getString("carrier").toString()
        val carrierTwoWay = bundle?.getString("carrierTwoWay").toString()

        val flightData = FlightData(departureDate, price, originPlace, destinationPlace, originIata.toString(), destinationIata.toString(), time.toString(), carrier, carrierTwoWay)

        intent.putExtra("flightData", flightData)
    }

    private fun generateRandomNumberOfSeats(limit: Int, bound: Int): LinkedHashSet<Int> {
        val ints = LinkedHashSet<Int>()
        val numbersOfElements = (0..limit).shuffled().first()
        for (i in 0..numbersOfElements) {
            ints.add(ThreadLocalRandom.current().nextInt(1, bound))
        }
        return ints
    }

    private fun showAlertDialogAdults() {
        //Inflate the dialog with custom view
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_numberof_adults, null)

        val npA: NumberPicker = dialogView.findViewById(R.id.numberPickerAdults) as NumberPicker
//        val npK: NumberPicker = dialogView.findViewById(R.id.numberPickerKids) as NumberPicker


        npA.minValue = 0
        npA.maxValue = 5
        npA.value = 1

        dialogBuilder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->

            val pickedValueAdults: Int = npA.value
//            val pickedValueKids: Int = npK.value
            // set your TextView id instead of R.id.textView1
            val tvA: TextView = this.findViewById(R.id.numberOf_seatsAdults) as TextView
//            val tvK: TextView = this.findViewById(R.id.numberOf_seatsKids) as TextView
            tvA.text = pickedValueAdults.toString()
//            tvK.text = Integer.toString(pickedValueKids)
            return@OnClickListener
        })
        dialogBuilder.setView(dialogView)

        //show dialog
        val mAlertDialog = dialogBuilder.show()
        //login button click of custom layout
//        mDialogView.dialogLoginBtn.setOnClickListener {
//            //dismiss dialog
//            mAlertDialog.dismiss()
//            //get text from EditTexts of custom layout
//            val name = mDialogView.dialogNameEt.text.toString()
//            val email = mDialogView.dialogEmailEt.text.toString()
//            val password = mDialogView.dialogPasswEt.text.toString()
//            //set the input text in TextView
//            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
//        }
//        //cancel button click of custom layout
//        mDialogView.dialogCancelBtn.setOnClickListener {
//            //dismiss dialog
//            mAlertDialog.dismiss()
//        }


    }

    private fun showAlertDialogKids() {
        //Inflate the dialog with custom view
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_numberof_kids, null)

        val npK: NumberPicker = dialogView.findViewById(R.id.numberPickerKids) as NumberPicker


        npK.minValue = 0
        npK.maxValue = 5
        npK.value = 0

        dialogBuilder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->

            val pickedValueKids: Int = npK.value
            // set your TextView id instead of R.id.textView1
            val tvK: TextView = this.findViewById(R.id.numberOf_seatsKids) as TextView
            tvK.text = pickedValueKids.toString()

            return@OnClickListener
        })
        dialogBuilder.setView(dialogView)

        //show dialog
        val mAlertDialog = dialogBuilder.show()
        //login button click of custom layout
//        mDialogView.dialogLoginBtn.setOnClickListener {
//            //dismiss dialog
//            mAlertDialog.dismiss()
//            //get text from EditTexts of custom layout
//            val name = mDialogView.dialogNameEt.text.toString()
//            val email = mDialogView.dialogEmailEt.text.toString()
//            val password = mDialogView.dialogPasswEt.text.toString()
//            //set the input text in TextView
//            mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)
//        }
//        //cancel button click of custom layout
//        mDialogView.dialogCancelBtn.setOnClickListener {
//            //dismiss dialog
//            mAlertDialog.dismiss()
//        }
    }

    private fun setupCustomSpinner(intent: Intent) {

        val adapter = SeatsArrayAdapter(this, Classes.list!!)

        select_class.adapter = adapter

        select_class.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedItem = parent!!.getItemAtPosition(position)

                selectedItem = selectedItem.toString()

                selectedItem = selectedItem.substring(selectedItem.indexOf("name=")+5, selectedItem.length -1)
                intent.putExtra("SeatClass", selectedItem)
                println(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}

class ClassSeatList(var seats: LinkedHashSet<Int>) : Serializable
class FlightData(var date: String, var price: String, var originPlace: String, var destinationPlace: String, var originIata: String, var destinationIata: String, var time: String, var carrier: String, var carrierTwoWay: String) : Serializable

//    private fun setupSimpleSpinner(){
//        val adapter = ArrayAdapter.createFromResource(this,
//            R.array.how_much_seats,
//            android.R.layout.simple_spinner_item)
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        amountOf_seats.adapter = adapter
//        amountOf_seats.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent!!.getItemAtPosition(position)
//                Toast.makeText(this@NewFlightDetails,
//                        "$selectedItem Selected",
//                            Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                //do nothing
//            }
//
//        }
//
//        }
//    }