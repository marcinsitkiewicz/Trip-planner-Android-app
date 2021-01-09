package com.example.trip_planner_andrid_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.flight_details.*
import kotlinx.android.synthetic.main.flight_details.seat1
import kotlinx.android.synthetic.main.plane_modal.*
import android.content.Intent
import com.example.trip_planner_andrid_app.flights.data.NewFlightDetails

class SelectSeatActivity: AppCompatActivity() {
    val seatsChecked = ArrayList<CheckBox>()
    var seatsHashMap : HashMap<CheckBox, String>
            = HashMap<CheckBox, String> ()

    val bottomSheetFragment = BottomSheetDialogConfirm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plane_modal)


        val numberOfAdults = intent.getIntExtra("NumberOfAdults", 0)
        val numberOfKids = intent.getIntExtra("NumberOfKids", 0)
        val totalNumber = numberOfAdults + numberOfKids
        System.out.println("numery $numberOfAdults $numberOfKids $totalNumber")
        val seatsChecked = ArrayList<CheckBox>()


        var numberOfCheckboxesChecked = 0
        fun seatListener(seat: CheckBox) {
            seat.setOnClickListener { isChecked ->
                if (numberOfCheckboxesChecked >= totalNumber.toInt()) {
                    if (seat.isChecked) {
                        seatsChecked.add(seat)
                        seatsChecked[0].isChecked = false
                        seatsChecked.removeAt(0)
                    } else {
                        seatsChecked.remove(seat)
                        numberOfCheckboxesChecked--
                    }
                } else {
                    if (seat.isChecked) {
                        numberOfCheckboxesChecked++
                        seatsChecked.add(seat)
                    } else {
                        numberOfCheckboxesChecked--
                        seatsChecked.remove(seat)
                    }
                }
                if(numberOfCheckboxesChecked == totalNumber.toInt()){
                    bottomSheetFragment.show(supportFragmentManager, "botomSheetDialog")
                }
            }
        }

        seatListener(seatEconomy1)
            seatsHashMap.put(seatEconomy1, "1A")
        seatListener(seatEconomy2)
            seatsHashMap.put(seatEconomy2, "1B")
        seatListener(seatEconomy3)
            seatsHashMap.put(seatEconomy3, "1C")
        seatListener(seatEconomy4)
            seatsHashMap.put(seatEconomy4, "1D")
        seatListener(seatEconomy5)
            seatsHashMap.put(seatEconomy5, "1E")
        seatListener(seatEconomy6)
            seatsHashMap.put(seatEconomy6, "1F")

        seatListener(seatEconomy7)
            seatsHashMap.put(seatEconomy7, "2A")
        seatListener(seatEconomy8)
            seatsHashMap.put(seatEconomy8, "2B")
        seatListener(seatEconomy9)
            seatsHashMap.put(seatEconomy9, "2C")
        seatListener(seatEconomy10)
            seatsHashMap.put(seatEconomy10, "2D")
        seatListener(seatEconomy11)
            seatsHashMap.put(seatEconomy11, "2E")
        seatListener(seatEconomy12)
            seatsHashMap.put(seatEconomy12, "2F")

        seatListener(seatEconomy13)
            seatsHashMap.put(seatEconomy13, "3A")
        seatListener(seatEconomy14)
            seatsHashMap.put(seatEconomy14, "3B")
        seatListener(seatEconomy15)
            seatsHashMap.put(seatEconomy15, "3C")
        seatListener(seatEconomy16)
            seatsHashMap.put(seatEconomy16, "3D")
        seatListener(seatEconomy17)
            seatsHashMap.put(seatEconomy17, "3E")
        seatListener(seatEconomy18)
            seatsHashMap.put(seatEconomy18, "3F")

        seatListener(seatEconomy19)
            seatsHashMap.put(seatEconomy19, "5A")
        seatListener(seatEconomy20)
            seatsHashMap.put(seatEconomy20, "5B")
        seatListener(seatEconomy21)
            seatsHashMap.put(seatEconomy21, "5C")
        seatListener(seatEconomy22)
            seatsHashMap.put(seatEconomy22, "5D")
        seatListener(seatEconomy23)
            seatsHashMap.put(seatEconomy23, "5E")
        seatListener(seatEconomy24)
            seatsHashMap.put(seatEconomy24, "5F")

        seatListener(seatEconomy25)
            seatsHashMap.put(seatEconomy25, "6A")
        seatListener(seatEconomy26)
            seatsHashMap.put(seatEconomy26, "6B")
        seatListener(seatEconomy27)
            seatsHashMap.put(seatEconomy27, "6C")
        seatListener(seatEconomy28)
            seatsHashMap.put(seatEconomy28, "6D")
        seatListener(seatEconomy29)
            seatsHashMap.put(seatEconomy29, "6E")
        seatListener(seatEconomy30)
            seatsHashMap.put(seatEconomy30, "6F")

        seatListener(seatEconomy31)
            seatsHashMap.put(seatEconomy31, "7A")
        seatListener(seatEconomy32)
            seatsHashMap.put(seatEconomy32, "7B")
        seatListener(seatEconomy33)
            seatsHashMap.put(seatEconomy33, "7C")
        seatListener(seatEconomy34)
            seatsHashMap.put(seatEconomy34, "7D")
        seatListener(seatEconomy35)
            seatsHashMap.put(seatEconomy35, "7E")
        seatListener(seatEconomy36)
            seatsHashMap.put(seatEconomy36, "7F")

        seatListener(seatEconomy37)
            seatsHashMap.put(seatEconomy37, "8A")
        seatListener(seatEconomy38)
            seatsHashMap.put(seatEconomy38, "8B")
        seatListener(seatEconomy39)
            seatsHashMap.put(seatEconomy39, "8C")
        seatListener(seatEconomy40)
            seatsHashMap.put(seatEconomy40, "8D")
        seatListener(seatEconomy41)
            seatsHashMap.put(seatEconomy41, "8E")
        seatListener(seatEconomy42)
            seatsHashMap.put(seatEconomy41, "8F")


        seatListener(seatBusiness1)
            seatsHashMap.put(seatBusiness1, "9A")
        seatListener(seatBusiness2)
            seatsHashMap.put(seatBusiness2, "9B")
        seatListener(seatBusiness3)
            seatsHashMap.put(seatBusiness3, "9C")
        seatListener(seatBusiness4)
            seatsHashMap.put(seatBusiness4, "9D")

        seatListener(seatBusiness5)
            seatsHashMap.put(seatBusiness5, "10A")
        seatListener(seatBusiness6)
            seatsHashMap.put(seatBusiness6, "10B")
        seatListener(seatBusiness7)
            seatsHashMap.put(seatBusiness7, "10C")
        seatListener(seatBusiness8)
            seatsHashMap.put(seatBusiness8, "10D")

        seatListener(seatBusiness9)
            seatsHashMap.put(seatBusiness9, "11A")
        seatListener(seatBusiness10)
            seatsHashMap.put(seatBusiness10, "11B")
        seatListener(seatBusiness11)
            seatsHashMap.put(seatBusiness11, "11C")
        seatListener(seatBusiness12)
            seatsHashMap.put(seatBusiness12, "11D")

        seatListener(seatBusiness13)
            seatsHashMap.put(seatBusiness13, "12A")
        seatListener(seatBusiness14)
            seatsHashMap.put(seatBusiness14, "12B")
        seatListener(seatBusiness15)
            seatsHashMap.put(seatBusiness15, "12C")
        seatListener(seatBusiness16)
            seatsHashMap.put(seatBusiness16, "12D")

        seatListener(seatPremium1)
            seatsHashMap.put(seatPremium1, "13A")
        seatListener(seatPremium2)
            seatsHashMap.put(seatPremium2, "13B")
        seatListener(seatPremium3)
            seatsHashMap.put(seatPremium3, "13C")
        seatListener(seatPremium4)
            seatsHashMap.put(seatPremium4, "13D")
        seatListener(seatPremium5)
            seatsHashMap.put(seatPremium5, "13E")
        seatListener(seatPremium6)
            seatsHashMap.put(seatPremium6, "13F")

        seatListener(seatPremium7)
            seatsHashMap.put(seatPremium7, "14A")
        seatListener(seatPremium8)
            seatsHashMap.put(seatPremium8, "14B")
        seatListener(seatPremium9)
            seatsHashMap.put(seatPremium9, "14C")
        seatListener(seatPremium10)
            seatsHashMap.put(seatPremium10, "14D")
        seatListener(seatPremium11)
            seatsHashMap.put(seatPremium11, "14E")
        seatListener(seatPremium12)
            seatsHashMap.put(seatPremium12, "14F")

        seatListener(seatPremium13)
            seatsHashMap.put(seatPremium13, "15A")
        seatListener(seatPremium14)
            seatsHashMap.put(seatPremium14, "15B")
        seatListener(seatPremium15)
            seatsHashMap.put(seatPremium15, "15C")
        seatListener(seatPremium16)
            seatsHashMap.put(seatPremium16, "15D")
        seatListener(seatPremium17)
            seatsHashMap.put(seatPremium17, "15E")
        seatListener(seatPremium18)
            seatsHashMap.put(seatPremium18, "15F")

        seatListener(seatPremium19)
            seatsHashMap.put(seatPremium19, "16A")
        seatListener(seatPremium20)
            seatsHashMap.put(seatPremium20, "16B")
        seatListener(seatPremium21)
            seatsHashMap.put(seatPremium21, "16C")
        seatListener(seatPremium22)
            seatsHashMap.put(seatPremium22, "16D")
        seatListener(seatPremium23)
            seatsHashMap.put(seatPremium23, "16E")
        seatListener(seatPremium24)
            seatsHashMap.put(seatPremium24, "16F")

        seatListener(seatsEconomic2_1)
            seatsHashMap.put(seatsEconomic2_1, "17A")
        seatListener(seatsEconomic2_2)
            seatsHashMap.put(seatsEconomic2_2, "17B")
        seatListener(seatsEconomic2_3)
            seatsHashMap.put(seatsEconomic2_3, "17C")
        seatListener(seatsEconomic2_4)
            seatsHashMap.put(seatsEconomic2_4, "17D")

        seatListener(seatsEconomic2_5)
            seatsHashMap.put(seatsEconomic2_5, "18A")
        seatListener(seatsEconomic2_6)
            seatsHashMap.put(seatsEconomic2_6, "18B")
        seatListener(seatsEconomic2_7)
            seatsHashMap.put(seatsEconomic2_7, "18C")
        seatListener(seatsEconomic2_8)
            seatsHashMap.put(seatsEconomic2_8, "18D")

        seatListener(seatsEconomic2_9)
            seatsHashMap.put(seatsEconomic2_9, "19A")
        seatListener(seatsEconomic2_10)
            seatsHashMap.put(seatsEconomic2_10, "19B")
        seatListener(seatsEconomic2_11)
            seatsHashMap.put(seatsEconomic2_11, "19C")
        seatListener(seatsEconomic2_12)
            seatsHashMap.put(seatsEconomic2_12, "19D")

        seatListener(seatsEconomic2_13)
            seatsHashMap.put(seatsEconomic2_13, "20A")
        seatListener(seatsEconomic2_14)
            seatsHashMap.put(seatsEconomic2_14, "20B")
        seatListener(seatsEconomic2_15)
            seatsHashMap.put(seatsEconomic2_15, "20C")
        seatListener(seatsEconomic2_16)
            seatsHashMap.put(seatsEconomic2_16, "20D")

    }
}