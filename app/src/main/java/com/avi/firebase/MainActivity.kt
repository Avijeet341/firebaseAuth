package com.avi.firebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextName:EditText
    lateinit var btnSend:Button
    lateinit var textViewName:TextView
    private val database:FirebaseDatabase= FirebaseDatabase.getInstance()
    private val reference:DatabaseReference=database.reference.child("User")
    private val reference2:DatabaseReference=database.reference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextName=findViewById(R.id.editTextName)
        btnSend=findViewById(R.id.buttonSend)
        textViewName=findViewById(R.id.textViewName)
          reference2.addValueEventListener(object :ValueEventListener{
              override fun onDataChange(snapshot: DataSnapshot) {
                  val realName: String = snapshot.child("User").child("userName").value as String
                  textViewName.text=realName
              }

              override fun onCancelled(error: DatabaseError) {
                  TODO("Not yet implemented")
              }

          })

        btnSend.setOnClickListener {
            val userName:String=editTextName.text.toString()
            reference.child("userName").setValue(userName)
        }
    }
}