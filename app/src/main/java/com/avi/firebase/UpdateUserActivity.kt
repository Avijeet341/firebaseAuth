
package com.avi.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.avi.firebase.databinding.ActivityMainBinding
import com.avi.firebase.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUserActivity : AppCompatActivity() {
    lateinit var updateUserBinding: ActivityUpdateUserBinding

    val database:FirebaseDatabase=FirebaseDatabase.getInstance()
    val myReference:DatabaseReference=database.reference.child("MyUsers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUserBinding= ActivityUpdateUserBinding.inflate(layoutInflater)
        val view=updateUserBinding.root
        setContentView(view)
        supportActionBar?.title="Update User"
        getAndSetData()

        updateUserBinding.buttonUpdateUser.setOnClickListener {
            updateData()
        }
    }

    fun getAndSetData(){
        //get data
        val name  =intent.getStringExtra("name")
        val age= intent.getIntExtra("age",0).toString()
        val email  =intent.getStringExtra("email")
        //set data
        updateUserBinding.editTextUpdateName.setText(name)
        updateUserBinding.editTextUpdateAge.setText(age)
        updateUserBinding.editTextUpdateEmailAddress.setText(email)
    }

    fun updateData(){
        val updatedName=updateUserBinding.editTextUpdateName.text.toString()
        val updatedAge=updateUserBinding.editTextUpdateAge.text.toString().toInt()
        val updatedEmail=updateUserBinding.editTextUpdateEmailAddress.text.toString()
        val userId=intent.getStringExtra("id").toString()

        val userMap= mutableMapOf<String,Any>()
        userMap["userId"]=userId
        userMap["userName"]=updatedName
        userMap["userAge"]=updatedAge
        userMap["userEmail"]=updatedEmail

        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task->

        if(task.isSuccessful){
            Toast.makeText(applicationContext,"The user has been updated", Toast.LENGTH_SHORT).show()
            finish()
        }

        }
    }
}