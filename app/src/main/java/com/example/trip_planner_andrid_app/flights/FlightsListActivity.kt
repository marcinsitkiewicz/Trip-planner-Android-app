package com.example.trip_planner_andrid_app.flights

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trip_planner_andrid_app.MainAdapter
import com.example.trip_planner_andrid_app.R
import com.example.trip_planner_andrid_app.flights.data.SkyscannerResults
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.flights_result_activity.*
import okhttp3.*
import java.io.IOException


@SuppressLint("Registered")
class FlightsListActivity : AppCompatActivity() {

    private lateinit var queryUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_result_activity)

        val originPlace = intent.getStringExtra("originPlace")
        val destinationPlace = intent.getStringExtra("destinationPlace")
        val outboundDateString = intent.getStringExtra("outboundDateString")
        val inboundDateString = intent.getStringExtra("inboundDateString")

        queryUrl = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/PL/PLN/pl-PL/" +
                originPlace +
                "/" +
                destinationPlace +
                "/" +
                outboundDateString +
                "/" +
                inboundDateString

        runOnUiThread {
            recyclerView_main.layoutManager = LinearLayoutManager(this)
        }
        callRequest(buildRequest(), createClient())
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient()
    }

    private fun buildRequest(): Request {
        return Request.Builder()
            .url(queryUrl)
            .get()
            .addHeader("x-rapidapi-key", "2fb44953c6msh66777e4d0355d7ep13efbejsn44e928f9677c")
            .addHeader("x-rapidapi-host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
            .build();
    }

    private fun callRequest(request: Request, client: OkHttpClient) {
        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { fetchJsonToDataClass(it) }
                }
            })
    }

    private fun fetchJsonToDataClass(body: String) {
        val gson = GsonBuilder().create()
        val searchFeed =  gson.fromJson(body, SkyscannerResults.SearchFeed::class.java)

        runOnUiThread {
            recyclerView_main.adapter = MainAdapter(searchFeed)
        }
    }
}