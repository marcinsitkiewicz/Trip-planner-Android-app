package com.example.trip_planner_andrid_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.firestore_activity.*
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8


class FirestoreConnectionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val userFlights = ArrayList<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firestore_activity)
        val currentUserID = Firebase.auth.currentUser?.uid.toString()
        println(currentUserID)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            val origin = inputOrigin.text.toString()
            val dest = inputDest.text.toString()
            val date = inputDate.text.toString()
            val price = inputPrice.text.toString()
            val carrier = "LOT"
            val hour = "12:00"
            val seatClass = "Klasa ekonomiczna"
            val seatArray = ArrayList<String>()
            seatArray.add("A1")
            seatArray.add("B2")
            seatArray.add("C3")


            saveUserFlight(origin, dest, date, price, carrier, hour, seatClass, seatArray)
        }

        getButton.setOnClickListener {
            getUserFlights()
        }
    }

    private fun saveUserFlight(origin: String, dest: String, date: String, price: String, carrier: String, hour: String, seatClass: String, seatArray: ArrayList<String>) {
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

        val stringToHash = origin + dest + date + price
        val flightHash = md5(stringToHash).toHex()
//        val currentUserID = "test123jyim5xqJsrQxB54Xr3w0IRHiA5r2"
        val currentUserID = Firebase.auth.currentUser?.uid.toString()


        db.collection("flights").document(flightHash)
            .set(flight)
            .addOnSuccessListener {
                    db.collection("users").document(currentUserID)
                        .update("flights", FieldValue.arrayUnion(flightHash))
                        .addOnSuccessListener {
                            Toast.makeText(this@FirestoreConnectionActivity, "record's fork added successfully to user database ", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@FirestoreConnectionActivity, "failed to add record's fork to user database ", Toast.LENGTH_LONG).show()
                        }
            }
            .addOnFailureListener{
                Toast.makeText(this@FirestoreConnectionActivity, "record adding to flights database failed ", Toast.LENGTH_LONG).show()
            }
    }

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    private fun getUserFlights() {
        val uid = Firebase.auth.currentUser?.uid.toString()
//        val uid = "jyim5xqJsrQxB54Xr3w0IRHiA5r2"
        val db = Firebase.firestore

        val userDocRef = uid.let { db.collection("users").document(it) }
        userDocRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val flightsHashes = task.result?.get("flights") as ArrayList<*>

                val flightsDocRef = db.collection("flights")
                flightsDocRef.get()
                    .addOnSuccessListener { documents ->
                        userFlights.clear()
                        for (document in documents) {
                            for (flight in flightsHashes) {
                                if (document.id == flight) {
                                    val documentArray = addFlightDataToArray(document)
                                    userFlights.add(documentArray)
                                }
                            }
                        }
                        println("Lista lotow uzytkownika: $userFlights")
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get documents from flights collection failed with ", exception)
                    }
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get documents from user collection failed with ", exception)
        }
    }

    fun addFlightDataToArray(document: QueryDocumentSnapshot): ArrayList<String> {
        val documentArray = ArrayList<String>()
        documentArray.add(document.data["date"] as String)
        documentArray.add(document.data["dest_place"] as String)
        documentArray.add(document.data["origin_place"] as String)
        documentArray.add(document.data["price"] as String)
        documentArray.add(document.data["carrier"] as String)
        documentArray.add(document.data["hour"] as String)
        documentArray.add(document.data["seatClass"] as String)
        documentArray.add((document.data["seatArray"] as ArrayList<*>).toString())

        return documentArray
    }

    companion object {
        private const val TAG = "FirestoreConnectionActivity"
    }
}
