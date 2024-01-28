package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class forgotActivity : AppCompatActivity() {

    lateinit var backButton: Button
    lateinit var RecoverButton:Button
    lateinit var forgotEmail: EditText
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        backButton = findViewById(R.id.forgotbackButton)
        RecoverButton = findViewById(R.id.RecoverPasswordButton)
        forgotEmail = findViewById(R.id.ForgotEmail);
        firebaseAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(){
            startActivity(Intent(this,MainActivity::class.java))
        }
        RecoverButton.setOnClickListener(){
            v:View->
            val EmailInString = forgotEmail.text.toString().trim()
            if(EmailInString.isEmpty())
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            else{
                //recovering
                firebaseAuth.sendPasswordResetEmail(EmailInString).addOnCompleteListener(){
                    task : Task<Void> ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Email send", Toast.LENGTH_SHORT).show()
                        finish();
                        startActivity(Intent(this,MainActivity::class.java))
                    }else
                        Toast.makeText(this, "Failed Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}