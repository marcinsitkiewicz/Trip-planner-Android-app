package com.example.trip_planner_andrid_app.flights.data

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.NumberPicker
import android.widget.TextView
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
        setContentView(R.layout.flight_details_new)

        val bundle = intent.extras

        val originIata = bundle?.getString("originIata")
        val destinationIata = bundle?.getString("destinationIata")
        val time = bundle?.getString("time")

        val originIataTextView: TextView = this.findViewById(R.id.origin_iata) as TextView
        val destinationIataTextView: TextView = this.findViewById(R.id.destination_iata) as TextView
        val outboundTime: TextView = this.findViewById(R.id.outbound_time) as TextView

        val numberOfAdults: TextView = this.findViewById(R.id.numberOf_seatsAdults) as TextView
        val numberOfKids: TextView = this.findViewById(R.id.numberOf_seatsKids) as TextView

        val classSeat:String

        val intent = Intent(this, SelectSeatActivity::class.java)

        originIataTextView.text = originIata
        destinationIataTextView.text = destinationIata
        outboundTime.text = time

        setupCustomSpinner(intent)

        select_numberOf_Adults.setOnClickListener { showAlertDialogAdults() }
        select_numberOf_Kids.setOnClickListener { showAlertDialogKids() }
        seatsButton.setOnClickListener() {
            val pickedValueAdults: Int = Integer.parseInt(numberOfAdults.text.toString())
            val pickedValueKids: Int = Integer.parseInt(numberOfKids.text.toString())
            println("\n\n\n" + pickedValueAdults + "\n\n" + pickedValueKids + "\n\n\n")

            intent.putExtra("NumberOfAdults", pickedValueAdults)
            intent.putExtra("NumberOfKids", pickedValueKids)
            setIntent(intent)
            startActivity(intent)
        }

        val reservedEconomySeats = generateRandomNumberOfSeats(20, 59)
        val reservedBusinessSeats = generateRandomNumberOfSeats(12, 16)
        val reservedPremiumSeats = generateRandomNumberOfSeats(20, 24)

        val economyClass = Class(reservedEconomySeats)
        val businessClass = Class(reservedBusinessSeats)
        val premiumClass = Class(reservedPremiumSeats)

        intent.putExtra("reservedEconomySeats", economyClass)
        intent.putExtra("reservedBusinessSeats", businessClass)
        intent.putExtra("reservedPremiumSeats", premiumClass)

    }

    fun generateRandomNumberOfSeats(limit: Int, bound: Int): LinkedHashSet<Int> {
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
        npA.maxValue = 10
        npA.value = 0

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
        npK.maxValue = 10
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

                selectedItem = selectedItem.toString();

                selectedItem = selectedItem.substring(selectedItem.indexOf("name=")+5, selectedItem.length -1);
                intent.putExtra("SeatClass", selectedItem)
                System.out.println(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}

class Class(var seats: LinkedHashSet<Int>) : Serializable

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