package com.example.firebase.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebase.models.EmployeeModel
import com.example.firebase.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEnterName : EditText
    private lateinit var etEnterCity : EditText
    private lateinit var etEnterCharges : EditText
    private lateinit var etEntreDesc : EditText
    private lateinit var btnPost : Button
   // private lateinit var btnCancel : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEnterName = findViewById(R.id.etEnterName)
        etEnterCity = findViewById(R.id.etEnterCity)
        etEnterCharges = findViewById(R.id.etEnterCharges)
        etEntreDesc = findViewById(R.id.etEntreDesc)
        btnPost = findViewById(R.id.btnPost)
        //btnCancel = findViewById(R.id.btnCancel)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnPost.setOnClickListener{
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData(){

        //getting values
        val empName = etEnterName.text.toString()
        val empCity = etEnterCity.text.toString()
        val empDesc = etEntreDesc.text.toString()
        val empCharges = etEnterCharges.text.toString()

        if (empName.isEmpty()){
            etEnterName.error = "Please enter name"
        }
        if (empCity.isEmpty()){
            etEnterCity.error = "Please enter city"
        }
        if (empDesc.isEmpty()){
            etEntreDesc.error = "Please enter Desc"
        }
        if (empCharges.isEmpty()){
            etEnterCharges.error = "Please enter charges per day"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empCity, empDesc, empCharges)

        dbRef.child(empId).setValue(employee)

            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEnterName.text.clear()
                etEnterCity.text.clear()
                etEntreDesc.text.clear()
                etEnterCharges.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }


}


