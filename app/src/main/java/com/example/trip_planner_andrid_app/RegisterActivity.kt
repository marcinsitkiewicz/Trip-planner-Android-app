package com.example.trip_planner_andrid_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.register_screen_activity.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen_activity)

        auth = Firebase.auth

        clickableTextView_login.setOnClickListener() {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register_button.setOnClickListener {
            closeKeyBoard()
            showProgressBar(true)

            val email = register_field_email.text.toString()
            val password = register_field_password.text.toString()
            val name = register_field_name.text.toString()
            val lastname = register_field_lastname.text.toString()

            createAccount(email, password, name, lastname)
        }
    }

    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user == null) {
            register_field_name.setText("")
            register_field_lastname.setText("")
            register_field_email.setText("")
            register_field_password.setText("")
            field_password_rpt.setText("")
        }
    }

    private fun addUserToDatabase(name: String, lastname: String) {
        val user = auth.currentUser
        val uid = user?.uid.toString()
        val db = Firebase.firestore

        val newUser = hashMapOf(
            "flights" to arrayListOf<String>(),
            "name" to name,
            "lastname" to lastname
        )

        db.collection("users").document(uid).set(newUser)
    }

    private fun createAccount(email: String, password: String, name: String, lastname: String) {
        if (!validateForm()) {
            showProgressBar(false)
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name, lastname)
                    showProgressBar(false)
                    finish()
                    startActivity(Intent(this, SearchForFlightsActivity()::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Operacja się nie powiodła.",
                        Toast.LENGTH_LONG).show()
                    showProgressBar(false)
                }
            }
            .addOnFailureListener(this) {
                showProgressBar(false)
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val name = register_field_name.text.toString()
        if (TextUtils.isEmpty(name)) {
            register_field_name.error = "To pole jest wymagane."
            valid = false
        }
        else {
            register_field_name.error = null
        }

        val lastname = register_field_lastname.text.toString()
        if (TextUtils.isEmpty(lastname)) {
            register_field_lastname.error = "To pole jest wymagane."
            valid = false
        }
        else {
            register_field_lastname.error = null
        }

        val email = register_field_email.text.toString()
        if (TextUtils.isEmpty(email)) {
            register_field_email.error = "To pole jest wymagane."
            valid = false
        }
        else if (!isEmailValid(email)) {
            register_field_email.error = "Wprowadź poprawny adres email\n" +
                                "np.: twoje.imie@domena.pl"
            valid = false
        }
        else {
            register_field_email.error = null
        }

        val password = register_field_password.text.toString()
        val passwordRpt = field_password_rpt.text.toString()
        if (TextUtils.isEmpty(password)) {
            register_field_password.error = "To pole jest wymagane."
            valid = false
        }
        else if (TextUtils.isEmpty(passwordRpt)) {
            field_password_rpt.error = "To pole jest wymagane."
            valid = false
        }
        else if (!isPasswordValid(password)) {
            register_field_password.error = "Hasło musi zawierać:\n" +
                    "- od 8 do 30 znaków\n" +
                    "- co najmniej 1 cyfrę, 1 małą oraz 1 wielką literę\n"
            valid = false
        }
        else if (!arePasswordsMatching(password, passwordRpt)) {
            field_password_rpt.error = "Hasła muszą być takie same."
            valid = false
        }
        else {
            register_field_password.error = null
        }

        return valid
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,30}$")
        return regex.matcher(password).matches()
    }

    private fun arePasswordsMatching(password: String, passwordRpt: String): Boolean {
        return password == passwordRpt
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        }
        else {
            progressBar.visibility = View.GONE
        }
    }
}