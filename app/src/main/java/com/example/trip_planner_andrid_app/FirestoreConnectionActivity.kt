package com.example.trip_planner_andrid_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.firestore_activity.*


class FirestoreConnectionActivity : AppCompatActivity() {

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

            saveFireStore(origin, dest, date, price)
        }
    }

    private fun saveFireStore(origin: String, dest: String, date: String, price: String) {
        val db = Firebase.firestore
        val flight: MutableMap<String, Any> = HashMap()
        flight["origin_place"] = origin
        flight["dest_place"] = dest
        flight["date"] = date
        flight["price"] = price


        db.collection("flights")
            .add(flight)
            .addOnSuccessListener {
                Toast.makeText(this@FirestoreConnectionActivity, "record added successfully ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@FirestoreConnectionActivity, "record adding failed ", Toast.LENGTH_SHORT).show()
            }
    }
}
