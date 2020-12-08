package com.example.trip_planner_andrid_app.flights.data

import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trip_planner_andrid_app.R
import kotlinx.android.synthetic.main.flight_details_new.*


class NewFlightDetails: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flight_details_new)
//        setupSimpleSpinner()
        setupCustomSpinner()

        select_numberOf_seats.setOnClickListener{showAlertDialog()}
    }

    private fun showAlertDialog() {
        //Inflate the dialog with custom view
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_numberof_seats, null)

        val npA: NumberPicker = dialogView.findViewById(R.id.numberPickerAdults) as NumberPicker

        npA.minValue = 0
        npA.maxValue = 10
        npA.value = 0

        dialogBuilder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
            val pickedValueAdults: Int = npA.value
            // set your TextView id instead of R.id.textView1
            val tv: TextView = this.findViewById(R.id.numberOf_seats) as TextView
            tv.text = Integer.toString(pickedValueAdults)
            return@OnClickListener
        })
        dialogBuilder.setView(dialogView)

        //show dialog
        val  mAlertDialog = dialogBuilder.show()
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

    private fun setupCustomSpinner(){
        val adapter = SeatsArrayAdapter(this, Classes.list!!)

        select_class.adapter = adapter
    }

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
    }