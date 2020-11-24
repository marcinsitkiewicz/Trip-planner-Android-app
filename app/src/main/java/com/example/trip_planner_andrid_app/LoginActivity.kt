package com.example.trip_planner_andrid_app
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_screen_activity.*
import kotlinx.android.synthetic.main.login_screen_activity.clickableTextView_register
import kotlinx.android.synthetic.main.register_screen_activity.*


public class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen_activity)

        clickableTextView_register.setOnClickListener() {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}