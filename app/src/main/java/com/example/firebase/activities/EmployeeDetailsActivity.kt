package com.example.firebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebase.R
import com.example.firebase.models.EmployeeModel
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId : TextView
    private lateinit var tvEmpName : TextView
    private lateinit var tvEmpCity : TextView
    private lateinit var tvEmpDesc : TextView
    private lateinit var tvEmpCharges : TextView
    private lateinit var btnDelete : Button
    private lateinit var btnUpdate : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString(),
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        //tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpCity = findViewById(R.id.tvEmpCity)
        tvEmpCharges = findViewById(R.id.tvEmpCharges)
        tvEmpDesc = findViewById(R.id.tvEmpDesc)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun setValuesToViews(){
        //tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpCity.text = intent.getStringExtra("empCity")
        tvEmpCharges.text = intent.getStringExtra("empCharges")
        tvEmpDesc.text = intent.getStringExtra("empDesc")
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEnterName = mDialogView.findViewById<EditText>(R.id.etEnterName)
        val etEnterCity = mDialogView.findViewById<EditText>(R.id.etEnterCity)
        val etEnterCharges = mDialogView.findViewById<EditText>(R.id.etEnterCharges)
        val etEntreDesc = mDialogView.findViewById<EditText>(R.id.etEntreDesc)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEnterName.setText(intent.getStringExtra("empName").toString())
        etEnterCity.setText(intent.getStringExtra("empCity").toString())
        etEnterCharges.setText(intent.getStringExtra("empCharges").toString())
        etEntreDesc.setText(intent.getStringExtra("empDesc").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateEmpData(
                empId,
                etEnterName.text.toString(),
                etEnterCity.text.toString(),
                etEnterCharges.text.toString(),
                etEntreDesc.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textview
            tvEmpName.text = etEnterName.text.toString()
            tvEmpCity.text = etEnterCity.text.toString()
            tvEmpCharges.text = etEnterCharges.text.toString()
            tvEmpDesc.text = etEntreDesc.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        city: String,
        chargesPerDay: String,
        desc: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, city, chargesPerDay, desc)
        dbRef.setValue(empInfo)
    }
}
