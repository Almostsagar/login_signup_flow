package com.sagar.login_signup_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference?.child("profile")
        register()
    }

    private fun register() {
        val button = findViewById<Button>(R.id.signUp_Button)
        val firstName = findViewById<TextInputEditText>(R.id.firstName)
        val lastName = findViewById<TextInputEditText>(R.id.last_name)
        val password = findViewById<TextInputEditText>(R.id.password)
        val emailId = findViewById<TextInputEditText>(R.id.email)
        button.setOnClickListener {
            if (TextUtils.isEmpty(firstName.text.toString())) {
                firstName.setError("Please Enter First Name")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastName.text.toString())) {
                firstName.setError("Please Enter Last Name")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(emailId.text.toString())) {
                firstName.setError("Please Enter Email Id")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(password.text.toString())) {
                firstName.setError("Please Enter Password Name")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(emailId.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUserdb = databaseReference?.child((currentUser?.uid!!))
                            currentUserdb!!.child("firstname").setValue(firstName.text.toString())
                            currentUserdb!!.child("lastname").setValue(lastName.text.toString())
                            Toast.makeText(this@RegisterActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration failed pls try again !", Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
}