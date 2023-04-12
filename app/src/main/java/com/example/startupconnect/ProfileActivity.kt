package com.example.startupconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {
    //Views
    private lateinit var imageView: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewPhone: EditText
    private lateinit var editButtonPhone: ImageButton
    private lateinit var editButtonImage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        //init view
        initViews()

        //event handling
        editButtonPhoneEvent()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        textViewName = findViewById(R.id.textViewName)
        textViewPhone = findViewById(R.id.textViewPhone)
        editButtonPhone = findViewById(R.id.editButtonPhone)
        editButtonImage = findViewById(R.id.editButtonImage)
    }

    private fun editButtonPhoneEvent(){
        editButtonPhone.setOnClickListener {
            editButtonPhone.isEnabled = editButtonPhone.isEnabled != true
        }
    }
}