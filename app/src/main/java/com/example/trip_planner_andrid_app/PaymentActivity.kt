package com.example.trip_planner_andrid_app

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.payment_screen.*

class PaymentActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_screen)

        btn_pay.setOnClickListener {
            payment.playAnimation()

        }



        payment.addAnimatorUpdateListener { valueAnimator ->
            // Set animation progress
            val progress = (valueAnimator.animatedValue as Float * 100).toInt()

            if (progress == 59) {
                setContentView(R.layout.payment_success_screen)
                congratulation.setMinAndMaxProgress(0.0f, 0.5f)
            }
        }
    }

    override fun onBackPressed() {
        return
    }

}
