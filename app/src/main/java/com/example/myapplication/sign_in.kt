package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class sign_in : AppCompatActivity() {

    private lateinit var loginPage:LinearLayout
    lateinit var backButton:Button
    lateinit var SignupButton:Button
    lateinit var Email:EditText
    lateinit var password:EditText
    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        loginPage = findViewById(R.id.LoginWindowClick)
        backButton = findViewById(R.id.SignInbackButton)
        SignupButton = findViewById(R.id.SignButton)
        Email = findViewById(R.id.SignUpEmail)
        password = findViewById(R.id.SignUpPassword)
        firebaseAuth = FirebaseAuth.getInstance()

        backButton.setOnClickListener() {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        loginPage.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        SignupButton.setOnClickListener() {
            val emailString: String = Email.text.toString().trim()
            val PasswordString = password.text.toString().trim()
            if (emailString.isEmpty() && PasswordString.isEmpty())
                Toast.makeText(this, "Enter info", Toast.LENGTH_SHORT).show()
            else if (emailString.isEmpty())
                Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show()
            else if (PasswordString.isEmpty())
                Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show()
            else {
                // firebaseAuth means firebaseAuthentication
                firebaseAuth.createUserWithEmailAndPassword(emailString, PasswordString)
                    .addOnCompleteListener() {
                        task:Task<AuthResult>->
                        if(task.isSuccessful){
                            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                            SendEmailVerification()
                        }else
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
    private fun SendEmailVerification() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(){
                Toast.makeText(this,"verify your email by clicking the link in your email",Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut();
                startActivity(Intent(this, MainActivity::class.java))
            }
        }else{
            Toast.makeText(this, "Email verification failed", Toast.LENGTH_SHORT).show()
        }
    }
}