package com.riki.net89

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.riki.net89.helper.Constants
import com.riki.net89.helper.PreferencesHelper

class MainActivity : AppCompatActivity() {

    lateinit var sharedpref : PreferencesHelper
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpref = PreferencesHelper(this)
        auth = FirebaseAuth.getInstance()

        //GET WIDGET
        val sButton : Button = findViewById(R.id.submitButton)
        val editUsername :EditText = findViewById(R.id.editUsername)
        val editPassword :EditText = findViewById(R.id.editPassword)
        val tvRegister : TextView = findViewById(R.id.tvRegister)
        val tvForgot : TextView = findViewById(R.id.forgotPassword)

        //TEXT VIEW FORGOT
        tvForgot.setOnClickListener {
            val emailAddress = "1177050100@student.uinsgd.ac.id"

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast("Password Reset Has Been Sent")
                        }
                    }
        }

        //TEXT VIEW REGISTER LISTENER
        tvRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
            showToast("Register Activity!")
        }

        //BUTTON SUBMIT LISTENER
        sButton.setOnClickListener {

            //AUTH FIREBASE
            auth.signInWithEmailAndPassword(editUsername.text.toString(), editPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            saveSession(editUsername.text.toString(), editPassword.text.toString())
                            showToast("Berhasil Masuk, Alhamdulillah !")
                            updateUI(user)

                        } else {
                            showToast("Username atau Password salah !")
                            updateUI(null)
                        }

                        // ...
                    }
        }
    }

    // When Apps Started
    override fun onStart(){
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    //CHECK USER
    fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified) {
                moveIntent()
            }
            else{
                showToast("Please Verify Your Email Adress !!")
            }
        }
    }

    //SAVE SESSION
    private fun saveSession(username : String, password : String){
        sharedpref.simpanDataStr(Constants.PREF_USERNAME, username)
        sharedpref.simpanDataStr(Constants.PREF_PASSWORD, password)
        sharedpref.simpanBool(Constants.PREF_IS_LOGIN, true)
    }

    //Move Layout
    private fun moveIntent(){
        startActivity(Intent(this,UserActivity::class.java))
        finish()
    }

    //Message Pop-Up
    private fun showToast(message : String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}