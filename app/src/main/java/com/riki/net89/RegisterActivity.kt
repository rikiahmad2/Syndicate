package com.riki.net89

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var registerUsername : EditText
    private lateinit var registerButton : Button
    private lateinit var registerPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        //GET WIDGET
        registerButton= findViewById(R.id.registerButton)
        registerUsername = findViewById(R.id.signUsername)
        registerPassword= findViewById(R.id.signPassword)

        registerButton.setOnClickListener{
            registerUser()
        }
    }

    //REGISTER NEW USER
    private fun registerUser(){

        if(registerUsername.text.toString().isEmpty()){
            registerUsername.error = "Please Enter Email"
            registerUsername.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(registerUsername.text.toString()).matches()){
            registerUsername.error = "Please Enter Valid Email"
            registerUsername.requestFocus()
            return
        }
        if(registerPassword.text.toString().isEmpty()){
            registerPassword.error = "Please Enter Password"
            registerPassword.requestFocus()
            return
        }

        //REGISTER NEW ACCOUNT
        auth.createUserWithEmailAndPassword(registerUsername.text.toString(), registerPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // SEND VERIFICATION EMAIL
                       val user = auth.currentUser
                        user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        startActivity(Intent(this,MainActivity::class.java))
                                        finish()
                                    }
                                }
                    }
                    else {
                        Toast.makeText(baseContext, "Register Failed, Try Again Later", Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }
}