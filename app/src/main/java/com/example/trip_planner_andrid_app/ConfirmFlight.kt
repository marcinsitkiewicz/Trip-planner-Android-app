package com.example.trip_planner_andrid_app

//import kotlinx.android.synthetic.main.bottomsheet_fragment.view.*
import SeatIdAdapter
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trip_planner_andrid_app.flights.data.FlightData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mapbox.mapboxsdk.style.expressions.Expression.number
import kotlinx.android.synthetic.main.modal_confirm_seats.*
import java.security.MessageDigest
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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

        val flightData = intent.getSerializableExtra("flightData") as FlightData
        println(
            flightData.date.substring(
                0,
                10
            ) + ", " + flightData.price + ", " + flightData.time + ", " + seatValues.value
        )

        var price: Double = flightData.price.toDouble()
        if ((seatClass.toString()) == "Klasa Business") {
            price *= 2.50
        } else if ((seatClass.toString()) == "Klasa Premium") {
            price *= 1.70
        }
        price *= seatValues.value.size
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        var currency: String = format.format(price)
        currency = currency.substring(0, currency.length - 3)
        findViewById<TextView>(R.id.price).text = "$currency PLN"

        findViewById<TextView>(R.id.originIata).text = flightData.originIata
        findViewById<TextView>(R.id.destinationIata).text = flightData.destinationIata
        findViewById<TextView>(R.id.originPlace).text = flightData.originPlace
        findViewById<TextView>(R.id.destinationPlace).text = flightData.destinationPlace
        findViewById<TextView>(R.id.timeOutbound).text = flightData.time

        val weekDayFormat = SimpleDateFormat("EEEE")
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val d: Date = sdf.parse(flightData.date.substring(0, 10))
        val dayOfTheWeek: String = weekDayFormat.format(d)
        findViewById<TextView>(R.id.weekDay).text = dayOfTheWeek
        findViewById<TextView>(R.id.date).text = flightData.date.substring(0, 10)


        payButton.setOnClickListener {
//            Saving flight data to Firebase
            saveUserFlight(
                flightData.originPlace,
                flightData.destinationPlace,
                flightData.date.substring(0, 10),
                currency,
                flightData.carrier,
                flightData.time,
                seatClass.toString(),
                seatValues.value
            )
        }
    }

    private fun saveUserFlight(
        origin: String,
        dest: String,
        date: String,
        price: String,
        carrier: String,
        hour: String,
        seatClass: String,
        seatArray: ArrayList<String>
    ) {
        val db = Firebase.firestore
        val flight: MutableMap<String, Any> = HashMap()
        flight["origin_place"] = origin
        flight["dest_place"] = dest
        flight["date"] = date
        flight["price"] = price
        flight["carrier"] = carrier
        flight["hour"] = hour
        flight["seatClass"] = seatClass
        flight["seatArray"] = seatArray

        val stringToHash = origin + dest + date + price + seatClass + seatArray.toString()
        val flightHash = md5(stringToHash).toHex()
//        val currentUserID = "test123jyim5xqJsrQxB54Xr3w0IRHiA5r2"
        val currentUserID = Firebase.auth.currentUser?.uid.toString()


        db.collection("flights").document(flightHash)
            .set(flight)
            .addOnSuccessListener {
                db.collection("users").document(currentUserID)
                    .update("flights", FieldValue.arrayUnion(flightHash))
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@ConfirmFlight,
                            "record's fork added successfully to user database ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@ConfirmFlight,
                            "failed to add record's fork to user database ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            .addOnFailureListener{
                Toast.makeText(this@ConfirmFlight, "record adding to flights database failed ", Toast.LENGTH_LONG).show()
            }
    }

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}