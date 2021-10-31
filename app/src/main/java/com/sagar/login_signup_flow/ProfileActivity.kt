package com.sagar.login_signup_flow

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference?.child("profile")
        loadProfile()
    }

    @SuppressLint("SetTextI18n")
    private fun loadProfile() {
        val user = auth.currentUser
        val userref = databaseReference?.child(user?.uid!!)
        val emailId = findViewById<TextView>(R.id.EmailText)
        val firstName = findViewById<TextView>(R.id.firstNameText)
        val lastName = findViewById<TextView>(R.id.LastNameText)
        val logoutbtn = findViewById<Button>(R.id.logout_Button)
        emailId.text = "Email --->"+ user?.email
        userref?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firstName.text = "First Name --->"+ snapshot.child("firstname").value.toString()
                lastName.text = "Last Name --->"+ snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        logoutbtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }
}