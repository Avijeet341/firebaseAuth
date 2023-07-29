package com.avi.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.avi.firebase.databinding.ActivityForgotBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {

    lateinit var forgotBinding:ActivityForgotBinding

      val auth:FirebaseAuth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotBinding=ActivityForgotBinding.inflate(layoutInflater)
        val view=forgotBinding.root
        setContentView(view)
        forgotBinding.buttonReset.setOnClickListener {
            val email=forgotBinding.editTextReset.text.toString()

            auth.sendPasswordResetEmail(email).addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext
                         ,"We sent a password reset mail to your mail adress"
                         ,Toast.LENGTH_SHORT).show()
                    finish()//return to login page
                }

            }
        }
    }
}