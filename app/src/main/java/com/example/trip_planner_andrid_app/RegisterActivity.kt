package com.example.trip_planner_andrid_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_screen_activity.*
import kotlinx.android.synthetic.main.register_screen_activity.*

public class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen_activity)


        clickableTextView_login.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}