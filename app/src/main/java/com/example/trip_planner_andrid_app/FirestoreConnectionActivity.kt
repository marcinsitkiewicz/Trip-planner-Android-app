package com.example.trip_planner_andrid_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            val origin = inputOrigin.text.toString()
            val dest = inputDest.text.toString()
            val date = inputDate.text.toString()
            val price = inputPrice.text.toString()

            saveUserFlight(origin, dest, date, price)
        }

        getButton.setOnClickListener {
            getUserFlights()
            println("Lista lotow uzytkownika: $userFlights")
        }
    }

    private fun saveUserFlight(origin: String, dest: String, date: String, price: String) {
        val db = Firebase.firestore
        val flight: MutableMap<String, Any> = HashMap()
        flight["origin_place"] = origin
        flight["dest_place"] = dest
        flight["origin_place2"] = ""
        flight["dest_place2"] = ""
        flight["date"] = date
        flight["date2"] = ""
        flight["price"] = price

        val stringToHash = origin + dest + date + price
        val flightHash = md5(stringToHash).toHex()
        val currentUserID = "jyim5xqJsrQxB54Xr3w0IRHiA5r2"


        db.collection("flights").document(flightHash)
            .set(flight)
            .addOnSuccessListener {
                db.collection("users").document(currentUserID)
                    .update("flights", FieldValue.arrayUnion(flightHash))
                    .addOnSuccessListener {
                        Toast.makeText(this@FirestoreConnectionActivity, "record's fork added successfully to user database ", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@FirestoreConnectionActivity, "record's fork added successfully to user database ", Toast.LENGTH_LONG).show()
                    }
                Toast.makeText(this@FirestoreConnectionActivity, "record's for adding to user database failed ", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@FirestoreConnectionActivity, "record adding to flights database failed ", Toast.LENGTH_LONG).show()
            }
    }

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    private fun getUserFlights() {
//        val uid = Firebase.auth.uid
        val uid = "jyim5xqJsrQxB54Xr3w0IRHiA5r2"
        val db = Firebase.firestore

        val userDocRef = db.collection("users").document(uid)
        userDocRef.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val flightsHashes = task.result?.get("flights") as ArrayList<*>

                    val flightsDocRef = db.collection("flights")
                    flightsDocRef.get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                for (flight in flightsHashes) {
                                    if (document.id == flight) {
                                        val documentArray = addFlightDataToArray(document)
                                        userFlights.add(documentArray)
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get documents from flights collection failed with ", exception)
                        }
                }
                else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get documents from user collection failed with ", exception)
            }
    }

    fun addFlightDataToArray(document: QueryDocumentSnapshot): ArrayList<String> {
        val documentArray = ArrayList<String>()
        documentArray.add(document.data["date"] as String)
        documentArray.add(document.data["date2"] as String)
        documentArray.add(document.data["dest_place"] as String)
        documentArray.add(document.data["dest_place2"] as String)
        documentArray.add(document.data["origin_place"] as String)
        documentArray.add(document.data["origin_place2"] as String)
        documentArray.add(document.data["price"] as String)

        return documentArray
    }

    companion object {
        private const val TAG = "FirestoreConnectionActivity"
    }
}
