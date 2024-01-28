package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var SignupPage: LinearLayout
    lateinit var forgotButton: TextView
    lateinit var Email: EditText
    lateinit var password:EditText
    lateinit var Loginbutton: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SignupPage = findViewById(R.id.SignUPWindowClick)
        forgotButton = findViewById(R.id.forgotPasswordButton)
        Email = findViewById(R.id.loginEmail)
        password = findViewById(R.id.loginPassword)
        Loginbutton = findViewById(R.id.LoginButton)
        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBarLogin)
        val firebaseUser = firebaseAuth.currentUser

        //if already logged in
        /*if (firebaseUser != null) {
            finish()
            startActivity(Intent(this@MainActivity, notePadlayout::class.java))
        }*/
        SignupPage.setOnClickListener {
            val intent = Intent(this@MainActivity, sign_in::class.java)
            startActivity(intent)
        }
        forgotButton.setOnClickListener(){
            startActivity(Intent(this,forgotActivity::class.java))
        }
        Loginbutton.setOnClickListener(){
            val EmailInString: String = Email.text.toString().trim()
            val passwordInString: String = password.text.toString().trim()
            if(EmailInString.isEmpty()&&passwordInString.isEmpty())
                Toast.makeText(this, "Enter info", Toast.LENGTH_SHORT).show()
            else if (EmailInString.isEmpty())
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            else if(passwordInString.isEmpty())
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            else{
                progressBar.visibility = View.VISIBLE
                // logging in
                firebaseAuth.signInWithEmailAndPassword(EmailInString,passwordInString).addOnCompleteListener(){
                        task: Task<AuthResult> ->
                    if(task.isSuccessful)
                        chickMailVerification()
                    else{
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "account doesn't exist", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
    fun chickMailVerification() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            if (firebaseUser.isEmailVerified) {
                finish()
                startActivity(Intent(this@MainActivity, notePadlayout::class.java))
            } else {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this, "verify your email", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
            }
        }
    }
}