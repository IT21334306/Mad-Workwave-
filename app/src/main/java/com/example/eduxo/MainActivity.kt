package com.example.eduxo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.eduxo.FetchingActivity
import com.example.eduxo.InsertionActivity
import com.example.eduxo.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button

    private lateinit var btnFetchData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()


        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)


        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

    }
}