package com.example.trip_planner_andrid_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_screen_activity.*
import com.scwang.wave.MultiWaveHeader

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen_activity)


        // Initialize Firebase Auth
        auth = Firebase.auth

        login_button.setOnClickListener {
            val email = field_email.text.toString()
            val password = field_password.text.toString()

            signIn(email, password)
        }

        clickableTextView_register.setOnClickListener() {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user == null) {
            field_email.setText("")
            field_password.setText("")
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, HomeActivity()::class.java))
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(baseContext, "Operacja się nie powiodła.",
                        Toast.LENGTH_LONG).show()
                }
            }
        closeKeyBoard()
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}