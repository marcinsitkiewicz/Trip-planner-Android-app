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
import android.widget.Button
import com.example.trip_planner_andrid_app.flights.data.NewFlightDetails
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_fragment.view.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SelectSeatActivity : AppCompatActivity() {
    val seatsChecked = ArrayList<CheckBox>()

    val seatsEconomyArray = ArrayList<CheckBox>()
    val seatsPremiumArray = ArrayList<CheckBox>()
    val seatsBusinessArray = ArrayList<CheckBox>()

    var seatsHashMap: HashMap<CheckBox, String> = HashMap<CheckBox, String>()

//    val bottomSheetFragment = BottomSheetDialogConfirm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plane_modal)

        val seatClass = intent.getStringExtra("SeatClass")
        val numberOfAdults = intent.getIntExtra("NumberOfAdults", 0)
        val numberOfKids = intent.getIntExtra("NumberOfKids", 0)
        val totalNumber = numberOfAdults + numberOfKids

        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_fragment, null)
        bottomSheetDialog.setContentView(view)
        view.btn1.setOnClickListener{
            startActivity(Intent(this, ConfirmFlight::class.java))
        }

        System.out.println("numery $numberOfAdults $numberOfKids $totalNumber")
        System.out.println("Klasa siedzeń $seatClass")

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
                if (numberOfCheckboxesChecked == totalNumber.toInt()) {
                    bottomSheetDialog.show()
                }
            }
        }

        fun generateRandomNumbers(): LinkedHashSet<Int> {
            val ints = LinkedHashSet<Int>()
            var numbersOfElements = ThreadLocalRandom.current().nextInt(20);

            for(i in 0..numbersOfElements){
                ints.add(ThreadLocalRandom.current().nextInt(1,41))
            }
            for(element in ints){
                print("$element ")
            }
            return ints
        }

        fun disableSeats() {
            if(seatClass.equals("Klasa Ekonomiczna")) {
                for (seat in seatsPremiumArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
                for (seat in seatsBusinessArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
                val reservedEconomySeats = generateRandomNumbers()
                var reservedSeat: CheckBox
                for(num in reservedEconomySeats){
                    reservedSeat = seatsEconomyArray.get(num)
                    reservedSeat.setBackgroundColor(R.drawable.bg_seat_plane_reserved)
                    reservedSeat.isEnabled = false
                    reservedSeat.isClickable = false
                }
            }else if(seatClass.equals("Klasa Business")){
                for (seat in seatsEconomyArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
                for (seat in seatsPremiumArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
            }else if(seatClass.equals("Klasa Premium")){
                for (seat in seatsBusinessArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
                for (seat in seatsEconomyArray) {
                    seat.isEnabled = false
                    seat.isClickable = false
                    seat.setBackgroundResource(R.drawable.bg_seat_plane_disabled)
                }
            }
        }

//        val ints: IntArray = Random().ints(1, 50).distinct().limit(6).toArray()

        val ints = LinkedHashSet<Int>()
        var numbersOfElements = ThreadLocalRandom.current().nextInt(20) + 10;

        for(i in 0..numbersOfElements){
            ints.add(ThreadLocalRandom.current().nextInt()*100)
        }
        for(element in ints){
            println(element)
        }



        seatListener(seatEconomy1)
        seatsHashMap.put(seatEconomy1, "1A")
        seatsEconomyArray.add(seatEconomy1)

        seatListener(seatEconomy2)
        seatsHashMap.put(seatEconomy2, "1B")
        seatsEconomyArray.add(seatEconomy2)

        seatListener(seatEconomy3)
        seatsHashMap.put(seatEconomy3, "1C")
        seatsEconomyArray.add(seatEconomy3)

        seatListener(seatEconomy4)
        seatsHashMap.put(seatEconomy4, "1D")
        seatsEconomyArray.add(seatEconomy4)

        seatListener(seatEconomy5)
        seatsHashMap.put(seatEconomy5, "1E")
        seatsEconomyArray.add(seatEconomy5)

        seatListener(seatEconomy6)
        seatsHashMap.put(seatEconomy6, "1F")
        seatsEconomyArray.add(seatEconomy6)


        seatListener(seatEconomy7)
        seatsHashMap.put(seatEconomy7, "2A")
        seatsEconomyArray.add(seatEconomy7)

        seatListener(seatEconomy8)
        seatsHashMap.put(seatEconomy8, "2B")
        seatsEconomyArray.add(seatEconomy8)

        seatListener(seatEconomy9)
        seatsHashMap.put(seatEconomy9, "2C")
        seatsEconomyArray.add(seatEconomy9)

        seatListener(seatEconomy10)
        seatsHashMap.put(seatEconomy10, "2D")
        seatsEconomyArray.add(seatEconomy10)

        seatListener(seatEconomy11)
        seatsHashMap.put(seatEconomy11, "2E")
        seatsEconomyArray.add(seatEconomy11)

        seatListener(seatEconomy12)
        seatsHashMap.put(seatEconomy12, "2F")
        seatsEconomyArray.add(seatEconomy12)


        seatListener(seatEconomy13)
        seatsHashMap.put(seatEconomy13, "3A")
        seatsEconomyArray.add(seatEconomy13)

        seatListener(seatEconomy14)
        seatsHashMap.put(seatEconomy14, "3B")
        seatsEconomyArray.add(seatEconomy14)

        seatListener(seatEconomy15)
        seatsHashMap.put(seatEconomy15, "3C")
        seatsEconomyArray.add(seatEconomy15)

        seatListener(seatEconomy16)
        seatsHashMap.put(seatEconomy16, "3D")
        seatsEconomyArray.add(seatEconomy16)

        seatListener(seatEconomy17)
        seatsHashMap.put(seatEconomy17, "3E")
        seatsEconomyArray.add(seatEconomy17)

        seatListener(seatEconomy18)
        seatsHashMap.put(seatEconomy18, "3F")
        seatsEconomyArray.add(seatEconomy18)

        seatListener(seatEconomy19)
        seatsHashMap.put(seatEconomy19, "5A")
        seatsEconomyArray.add(seatEconomy19)

        seatListener(seatEconomy20)
        seatsHashMap.put(seatEconomy20, "5B")
        seatsEconomyArray.add(seatEconomy20)

        seatListener(seatEconomy21)
        seatsHashMap.put(seatEconomy21, "5C")
        seatsEconomyArray.add(seatEconomy21)

        seatListener(seatEconomy22)
        seatsHashMap.put(seatEconomy22, "5D")
        seatsEconomyArray.add(seatEconomy22)

        seatListener(seatEconomy23)
        seatsHashMap.put(seatEconomy23, "5E")
        seatsEconomyArray.add(seatEconomy23)

        seatListener(seatEconomy24)
        seatsHashMap.put(seatEconomy24, "5F")
        seatsEconomyArray.add(seatEconomy24)

        seatListener(seatEconomy25)
        seatsHashMap.put(seatEconomy25, "6A")
        seatsEconomyArray.add(seatEconomy25)

        seatListener(seatEconomy26)
        seatsHashMap.put(seatEconomy26, "6B")
        seatsEconomyArray.add(seatEconomy26)

        seatListener(seatEconomy27)
        seatsHashMap.put(seatEconomy27, "6C")
        seatsEconomyArray.add(seatEconomy27)

        seatListener(seatEconomy28)
        seatsHashMap.put(seatEconomy28, "6D")
        seatsEconomyArray.add(seatEconomy28)

        seatListener(seatEconomy29)
        seatsHashMap.put(seatEconomy29, "6E")
        seatsEconomyArray.add(seatEconomy29)

        seatListener(seatEconomy30)
        seatsHashMap.put(seatEconomy30, "6F")
        seatsEconomyArray.add(seatEconomy30)


        seatListener(seatEconomy31)
        seatsHashMap.put(seatEconomy31, "7A")
        seatsEconomyArray.add(seatEconomy31)

        seatListener(seatEconomy32)
        seatsHashMap.put(seatEconomy32, "7B")
        seatsEconomyArray.add(seatEconomy32)

        seatListener(seatEconomy33)
        seatsHashMap.put(seatEconomy33, "7C")
        seatsEconomyArray.add(seatEconomy33)

        seatListener(seatEconomy34)
        seatsHashMap.put(seatEconomy34, "7D")
        seatsEconomyArray.add(seatEconomy34)

        seatListener(seatEconomy35)
        seatsHashMap.put(seatEconomy35, "7E")
        seatsEconomyArray.add(seatEconomy35)
        seatListener(seatEconomy36)

        seatsHashMap.put(seatEconomy36, "7F")
        seatsEconomyArray.add(seatEconomy36)


        seatListener(seatEconomy37)
        seatsHashMap.put(seatEconomy37, "8A")
        seatsEconomyArray.add(seatEconomy37)

        seatListener(seatEconomy38)
        seatsHashMap.put(seatEconomy38, "8B")
        seatsEconomyArray.add(seatEconomy38)

        seatListener(seatEconomy39)
        seatsHashMap.put(seatEconomy39, "8C")
        seatsEconomyArray.add(seatEconomy39)

        seatListener(seatEconomy40)
        seatsHashMap.put(seatEconomy40, "8D")
        seatsEconomyArray.add(seatEconomy40)

        seatListener(seatEconomy41)
        seatsHashMap.put(seatEconomy41, "8E")
        seatsEconomyArray.add(seatEconomy41)

        seatListener(seatEconomy42)
        seatsHashMap.put(seatEconomy42, "8F")
        seatsEconomyArray.add(seatEconomy42)



        seatListener(seatBusiness1)
        seatsHashMap.put(seatBusiness1, "9A")
        seatsBusinessArray.add(seatBusiness1)

        seatListener(seatBusiness2)
        seatsHashMap.put(seatBusiness2, "9B")
        seatsBusinessArray.add(seatBusiness2)

        seatListener(seatBusiness3)
        seatsHashMap.put(seatBusiness3, "9C")
        seatsBusinessArray.add(seatBusiness3)

        seatListener(seatBusiness4)
        seatsHashMap.put(seatBusiness4, "9D")
        seatsBusinessArray.add(seatBusiness4)


        seatListener(seatBusiness5)
        seatsHashMap.put(seatBusiness5, "10A")
        seatsBusinessArray.add(seatBusiness5)

        seatListener(seatBusiness6)
        seatsHashMap.put(seatBusiness6, "10B")
        seatsBusinessArray.add(seatBusiness6)

        seatListener(seatBusiness7)
        seatsHashMap.put(seatBusiness7, "10C")
        seatsBusinessArray.add(seatBusiness7)

        seatListener(seatBusiness8)
        seatsHashMap.put(seatBusiness8, "10D")
        seatsBusinessArray.add(seatBusiness8)


        seatListener(seatBusiness9)
        seatsHashMap.put(seatBusiness9, "11A")
        seatsBusinessArray.add(seatBusiness9)

        seatListener(seatBusiness10)
        seatsHashMap.put(seatBusiness10, "11B")
        seatsBusinessArray.add(seatBusiness10)

        seatListener(seatBusiness11)
        seatsHashMap.put(seatBusiness11, "11C")
        seatsBusinessArray.add(seatBusiness11)

        seatListener(seatBusiness12)
        seatsHashMap.put(seatBusiness12, "11D")
        seatsBusinessArray.add(seatBusiness12)


        seatListener(seatBusiness13)
        seatsHashMap.put(seatBusiness13, "12A")
        seatsBusinessArray.add(seatBusiness13)

        seatListener(seatBusiness14)
        seatsHashMap.put(seatBusiness14, "12B")
        seatsBusinessArray.add(seatBusiness14)

        seatListener(seatBusiness15)
        seatsHashMap.put(seatBusiness15, "12C")
        seatsBusinessArray.add(seatBusiness15)

        seatListener(seatBusiness16)
        seatsHashMap.put(seatBusiness16, "12D")
        seatsBusinessArray.add(seatBusiness16)


        seatListener(seatPremium1)
        seatsHashMap.put(seatPremium1, "13A")
        seatsPremiumArray.add(seatPremium1)

        seatListener(seatPremium2)
        seatsHashMap.put(seatPremium2, "13B")
        seatsPremiumArray.add(seatPremium2)

        seatListener(seatPremium3)
        seatsHashMap.put(seatPremium3, "13C")
        seatsPremiumArray.add(seatPremium3)

        seatListener(seatPremium4)
        seatsHashMap.put(seatPremium4, "13D")
        seatsPremiumArray.add(seatPremium4)

        seatListener(seatPremium5)
        seatsHashMap.put(seatPremium5, "13E")
        seatsPremiumArray.add(seatPremium5)

        seatListener(seatPremium6)
        seatsHashMap.put(seatPremium6, "13F")
        seatsPremiumArray.add(seatPremium6)


        seatListener(seatPremium7)
        seatsHashMap.put(seatPremium7, "14A")
        seatsPremiumArray.add(seatPremium7)

        seatListener(seatPremium8)
        seatsHashMap.put(seatPremium8, "14B")
        seatsPremiumArray.add(seatPremium8)

        seatListener(seatPremium9)
        seatsHashMap.put(seatPremium9, "14C")
        seatsPremiumArray.add(seatPremium9)

        seatListener(seatPremium10)
        seatsHashMap.put(seatPremium10, "14D")
        seatsPremiumArray.add(seatPremium10)

        seatListener(seatPremium11)
        seatsHashMap.put(seatPremium11, "14E")
        seatsPremiumArray.add(seatPremium11)

        seatListener(seatPremium12)
        seatsHashMap.put(seatPremium12, "14F")
        seatsPremiumArray.add(seatPremium12)


        seatListener(seatPremium13)
        seatsHashMap.put(seatPremium13, "15A")
        seatsPremiumArray.add(seatPremium13)

        seatListener(seatPremium14)
        seatsHashMap.put(seatPremium14, "15B")
        seatsPremiumArray.add(seatPremium14)

        seatListener(seatPremium15)
        seatsHashMap.put(seatPremium15, "15C")
        seatsPremiumArray.add(seatPremium15)

        seatListener(seatPremium16)
        seatsHashMap.put(seatPremium16, "15D")
        seatsPremiumArray.add(seatPremium16)

        seatListener(seatPremium17)
        seatsHashMap.put(seatPremium17, "15E")
        seatsPremiumArray.add(seatPremium17)

        seatListener(seatPremium18)
        seatsHashMap.put(seatPremium18, "15F")
        seatsPremiumArray.add(seatPremium18)


        seatListener(seatPremium19)
        seatsHashMap.put(seatPremium19, "16A")
        seatsPremiumArray.add(seatPremium19)

        seatListener(seatPremium20)
        seatsHashMap.put(seatPremium20, "16B")
        seatsPremiumArray.add(seatPremium20)

        seatListener(seatPremium21)
        seatsHashMap.put(seatPremium21, "16C")
        seatsPremiumArray.add(seatPremium21)

        seatListener(seatPremium22)
        seatsHashMap.put(seatPremium22, "16D")
        seatsPremiumArray.add(seatPremium22)

        seatListener(seatPremium23)
        seatsHashMap.put(seatPremium23, "16E")
        seatsPremiumArray.add(seatPremium23)

        seatListener(seatPremium24)
        seatsHashMap.put(seatPremium24, "16F")
        seatsPremiumArray.add(seatPremium24)


        seatListener(seatsEconomic2_1)
        seatsHashMap.put(seatsEconomic2_1, "17A")
        seatsEconomyArray.add(seatsEconomic2_1)

        seatListener(seatsEconomic2_2)
        seatsHashMap.put(seatsEconomic2_2, "17B")
        seatsEconomyArray.add(seatsEconomic2_2)

        seatListener(seatsEconomic2_3)
        seatsHashMap.put(seatsEconomic2_3, "17C")
        seatsEconomyArray.add(seatsEconomic2_3)

        seatListener(seatsEconomic2_4)
        seatsHashMap.put(seatsEconomic2_4, "17D")
        seatsEconomyArray.add(seatsEconomic2_4)


        seatListener(seatsEconomic2_5)
        seatsHashMap.put(seatsEconomic2_5, "18A")
        seatsEconomyArray.add(seatsEconomic2_5)

        seatListener(seatsEconomic2_6)
        seatsHashMap.put(seatsEconomic2_6, "18B")
        seatsEconomyArray.add(seatsEconomic2_6)

        seatListener(seatsEconomic2_7)
        seatsHashMap.put(seatsEconomic2_7, "18C")
        seatsEconomyArray.add(seatsEconomic2_7)

        seatListener(seatsEconomic2_8)
        seatsHashMap.put(seatsEconomic2_8, "18D")
        seatsEconomyArray.add(seatsEconomic2_8)


        seatListener(seatsEconomic2_9)
        seatsHashMap.put(seatsEconomic2_9, "19A")
        seatsEconomyArray.add(seatsEconomic2_9)

        seatListener(seatsEconomic2_10)
        seatsHashMap.put(seatsEconomic2_10, "19B")
        seatsEconomyArray.add(seatsEconomic2_10)

        seatListener(seatsEconomic2_11)
        seatsHashMap.put(seatsEconomic2_11, "19C")
        seatsEconomyArray.add(seatsEconomic2_11)

        seatListener(seatsEconomic2_12)
        seatsHashMap.put(seatsEconomic2_12, "19D")
        seatsEconomyArray.add(seatsEconomic2_12)


        seatListener(seatsEconomic2_13)
        seatsHashMap.put(seatsEconomic2_13, "20A")
        seatsEconomyArray.add(seatsEconomic2_13)

        seatListener(seatsEconomic2_14)
        seatsHashMap.put(seatsEconomic2_14, "20B")
        seatsEconomyArray.add(seatsEconomic2_14)

        seatListener(seatsEconomic2_15)
        seatsHashMap.put(seatsEconomic2_15, "20C")
        seatsEconomyArray.add(seatsEconomic2_15)

        seatListener(seatsEconomic2_16)
        seatsHashMap.put(seatsEconomic2_16, "20D")
        seatsEconomyArray.add(seatsEconomic2_16)


        disableSeats()

    }
}