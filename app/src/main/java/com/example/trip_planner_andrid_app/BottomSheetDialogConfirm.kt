package com.example.trip_planner_andrid_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_fragment.*
import androidx.fragment.app.FragmentActivity





class BottomSheetDialogConfirm: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }
    val dialog = ConfirmFlight()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        btn1.setOnClickListener(){
//            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//            dialog.show(fragmentManager, "customDialog")
//        }
    }

}