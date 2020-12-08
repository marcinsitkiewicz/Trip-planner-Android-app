package com.example.trip_planner_andrid_app.flights.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.trip_planner_andrid_app.R
import kotlinx.android.synthetic.main.spinner_item.view.*

class SeatsArrayAdapter(context: Context, seatsList: List<SeatClass>) : ArrayAdapter<SeatClass>(context, 0, seatsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val seat = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        view.seatImage.setImageResource(seat!!.image)
        view.seatName.text = seat.name
        return view
    }
}