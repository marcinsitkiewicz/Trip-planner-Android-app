package com.example.trip_planner_andrid_app.flights

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trip_planner_andrid_app.MainAdapter
import com.example.trip_planner_andrid_app.flights.data.SkyscannerResults
import com.example.trip_planner_andrid_app.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.flights_result_activity.*
import okhttp3.*
import java.io.IOException

@SuppressLint("Registered")
class FlightsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_result_activity)
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
            .url("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/PL/PLN/pl-PL/WAW-sky/LAX-sky/2020-12")
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
                    response.body?.string()?.let { gson(it) }
                }
            })
    }

    private fun gson(body: String) {
        println(body)
        val gson = GsonBuilder().create()
        val searchFeed =  gson.fromJson(body, SkyscannerResults.SearchFeed::class.java)

        runOnUiThread {
            recyclerView_main.adapter = MainAdapter(searchFeed)
        }
    }
}