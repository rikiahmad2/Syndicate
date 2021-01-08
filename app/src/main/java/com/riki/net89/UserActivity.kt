package com.riki.net89

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.riki.net89.helper.Constants
import com.riki.net89.helper.PostAdapter
import com.riki.net89.helper.PreferencesHelper
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class UserActivity : AppCompatActivity() {

    lateinit var sharedpref: PreferencesHelper
    private val list = ArrayList<Content>()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        sharedpref = PreferencesHelper(this)

        //GET WIDGET ID
        val btnLogout : Button = findViewById(R.id.buttonLogout)
        val userText : TextView = findViewById(R.id.textUsername)
        val rv : RecyclerView = findViewById(R.id.rvPost)
        val tvRes : TextView = findViewById(R.id.tvResponse)

        //GET FIREBASE AUTH
        auth = FirebaseAuth.getInstance()
        userText.text = auth.currentUser?.email.toString()

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        //API RETROFIT
        RetrofitClient.instance.getContents().enqueue(object : Callback<ContentResponse>{

            override fun onFailure(call: Call<ContentResponse>, t: Throwable) {
                tvRes.text = t.message
            }

            override fun onResponse(
                call: Call<ContentResponse>,
                response: Response<ContentResponse>
            ) {
                val responseCode  = response.code().toString()
                tvRes.text = responseCode
                if(response.body()?.status == true) response.body()?.let { list.addAll((it.data)) }

                val adapter = PostAdapter(list)
                rv.adapter = adapter

            }

        })

        //BUTTON LOGOUT
        btnLogout.setOnClickListener{
            auth.signOut()
            Toast.makeText(applicationContext, "Anda Keluar Firebase !!", Toast.LENGTH_SHORT).show()
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}