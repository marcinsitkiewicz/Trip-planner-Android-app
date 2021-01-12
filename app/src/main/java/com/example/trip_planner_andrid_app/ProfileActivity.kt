package com.example.trip_planner_andrid_app

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val userCurrentFlights = ArrayList<ArrayList<String>>()
    val userHistoryFlights = ArrayList<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)


        findBtn.setOnClickListener() {
            startActivity(Intent(this, SearchForFlightsActivity::class.java))
        }

        profile_settings.setOnClickListener() {
            Toast.makeText(this, "Ustawienia profilu", Toast.LENGTH_SHORT).show()
        }
        logout.setOnClickListener() {
            Toast.makeText(this, "Wyloguj", Toast.LENGTH_SHORT).show()
        }

        getUserFlights()
    }
    fun createCurrentReservationList()
    {


        val r1 = findViewById(R.id.reservation1) as RelativeLayout
        val r2 = findViewById(R.id.reservation2) as RelativeLayout
        val btn = findViewById(R.id.buttonMoreCurrent) as Button
        val findBtn = findViewById(R.id.findBtn) as Button

        val txt1 = r1.findViewById(R.id.reservation1_txt1) as TextView
        val txt2 = r1.findViewById(R.id.reservation1_txt2) as TextView
        val txt3 = r1.findViewById(R.id.reservation1_txt3) as TextView

        val txt5 = r2.findViewById(R.id.reservation2_txt1) as TextView
        val txt6 = r2.findViewById(R.id.reservation2_txt2) as TextView
        val txt7 = r2.findViewById(R.id.reservation2_txt3) as TextView

        if(userCurrentFlights.size==0)
        {
            println("no current reservations")

            r1.visibility = View.GONE;
            r2.visibility = View.GONE;
            btn.visibility = View.GONE;
            //r1.removeAllViews()
            //r2.removeAllViews()


            //(btn.getParent() as ViewManager).removeView(btn)
            btn.visibility = View.GONE;

            val txtEmpty1 = findViewById(R.id.emptyInfoCurrent) as TextView
            txtEmpty1.visibility = View.VISIBLE;


            findBtn.visibility = View.VISIBLE;
        }
        else
        {
            findBtn.visibility = View.GONE;
            if(userCurrentFlights.size==1)
            {
                println("only one")
                r2.visibility = View.GONE;
                btn.visibility = View.GONE;


                txt1.text = userCurrentFlights[0][4] //"Polska"
                txt2.text = userCurrentFlights[0][2]//"Kanada"
                txt3.text = userCurrentFlights[0][0]//"15.01.2021"

                r1.visibility = View.VISIBLE;
            }
            else if(userCurrentFlights.size>=2)
            {
                if(userCurrentFlights.size>2)
                    btn.visibility = View.VISIBLE;

                txt1.text = userCurrentFlights[0][4] //"Polska"
                txt2.text = userCurrentFlights[0][2]//"Kanada"
                txt3.text = userCurrentFlights[0][0]//"15.01.2021"


                txt5.text = userCurrentFlights[1][4] //"Polska"
                txt6.text = userCurrentFlights[1][2]//"Kanada"
                txt7.text = userCurrentFlights[1][0]//"15.01.2021"


                r1.visibility = View.VISIBLE;
                r2.visibility = View.VISIBLE;
            }
        }
    }

    fun createHistoryReservationList()
    {


        val r1 = findViewById(R.id.history1) as RelativeLayout
        val r2 = findViewById(R.id.history2) as RelativeLayout
        val btn = findViewById(R.id.buttonMoreHistory) as Button

        val txt1 = r1.findViewById(R.id.history1_txt1) as TextView
        val txt2 = r1.findViewById(R.id.history1_txt2) as TextView
        val txt3 = r1.findViewById(R.id.history1_txt3) as TextView

        val txt5 = r2.findViewById(R.id.history2_txt1) as TextView
        val txt6 = r2.findViewById(R.id.history2_txt2) as TextView
        val txt7 = r2.findViewById(R.id.history2_txt3) as TextView

        if(userHistoryFlights.size==0)
        {
            println("no history reservations")

            r1.visibility = View.GONE;
            r2.visibility = View.GONE;
            //r1.removeAllViews()
            //r2.removeAllViews()


            //(btn.getParent() as ViewManager).removeView(btn)
            btn.visibility = View.GONE;

            val txtEmpty1 = findViewById(R.id.emptyInfoHistory) as TextView
            txtEmpty1.visibility = View.VISIBLE;
        }
        else
        {
            if(userHistoryFlights.size==1)
            {
                println("only one")
                r2.visibility = View.GONE;
                btn.visibility = View.GONE;


                txt1.text = userHistoryFlights[0][4] //"Polska"
                txt2.text = userHistoryFlights[0][2]//"Kanada"
                txt3.text = userHistoryFlights[0][0]//"15.01.2021"

                r1.visibility = View.VISIBLE;
            }
            else if(userHistoryFlights.size>=2)
            {
                if(userHistoryFlights.size>2)
                    btn.visibility = View.VISIBLE;

                txt1.text = userHistoryFlights[0][4] //"Polska"
                txt2.text = userHistoryFlights[0][2]//"Kanada"
                txt3.text = userHistoryFlights[0][0]//"15.01.2021"


                txt5.text = userHistoryFlights[1][4] //"Polska"
                txt6.text = userHistoryFlights[1][2]//"Kanada"
                txt7.text = userHistoryFlights[1][0]//"15.01.2021"


                r1.visibility = View.VISIBLE;
                r2.visibility = View.VISIBLE;
            }
        }

    }


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
                                    userCurrentFlights.clear()
                                    userHistoryFlights.clear()
                                    for (document in documents) {
                                        for (flight in flightsHashes) {
                                            if (document.id == flight) {
                                                val documentArray = addFlightDataToArray(document)

                                                val sdf = SimpleDateFormat("dd/MM/yyyy")
                                                val strDate: Date = sdf.parse(documentArray[0])
                                                if (Date().after(strDate))
                                                    userHistoryFlights.add(documentArray)
                                                else
                                                    userCurrentFlights.add(documentArray)
                                            }
                                        }
                                    }
                                    println("Current: "+userCurrentFlights);
                                    println("history: "+userHistoryFlights);

                                    println("current size: "+userCurrentFlights.size)
                                    println("history size: "+userHistoryFlights.size)

                                    createCurrentReservationList()
                                    createHistoryReservationList()

                                }
                                .addOnFailureListener { exception ->
                                    Log.d(ProfileActivity.TAG, "get documents from flights collection failed with ", exception)
                                }
                    }
                    else {
                        println("no flights");
                        Log.d(ProfileActivity.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ProfileActivity.TAG, "get documents from user collection failed with ", exception)
                }
    }

    fun addFlightDataToArray(document: QueryDocumentSnapshot): ArrayList<String> {
        val documentArray = ArrayList<String>()
        //documentArray.add(document.data["date"] as String)
        documentArray.add("15/01/2021")
        documentArray.add(document.data["date2"] as String)
        documentArray.add(document.data["dest_place"] as String)
        documentArray.add(document.data["dest_place2"] as String)
        documentArray.add(document.data["origin_place"] as String)
        documentArray.add(document.data["origin_place2"] as String)
        documentArray.add(document.data["price"] as String)

        return documentArray
    }
    companion object {
        const val TAG = "ProfileActivity"
    }
}
