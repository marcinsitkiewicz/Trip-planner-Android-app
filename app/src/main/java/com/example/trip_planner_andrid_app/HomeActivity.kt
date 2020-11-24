package com.example.trip_planner_andrid_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.home_activity.*



class HomeActivity() : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        // Initialize Firebase Auth
        auth = Firebase.auth

        logoutButton.setOnClickListener{
            signOut()
        }
    }

    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            status.text = getString(R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified)
        }
        else
        {
            finish()
        }
    }

    private fun signOut() {
        auth.signOut()
        finish()
    }
}