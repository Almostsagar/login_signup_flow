package com.sagar.login_signup_flow
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        login()
//        val currentUser = auth.currentUser
//        if (currentUser != null){
//            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
//            finish()
//        }
    }

    private fun login() {
        val password = findViewById<TextInputEditText>(R.id.password1)
        val emailId = findViewById<TextInputEditText>(R.id.email1)
        val loginButton = findViewById<Button>(R.id.login_Button)
        val signup_redir = findViewById<TextView>(R.id.signup_redirection)
        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(emailId.text.toString())) {
                emailId.setError("Please Enter Email ID")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(password.text.toString())) {
                password.setError("Please Enter Password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(emailId.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login in Sex Sex",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed pls try again !",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        signup_redir.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

    }
}