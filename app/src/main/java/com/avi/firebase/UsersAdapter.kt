package com.avi.firebase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avi.firebase.databinding.UserItemBinding

class UsersAdapter(var context: Context,
  var userList: ArrayList<Users>):RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    inner class UserViewHolder(val adapterBinding:UserItemBinding)
        :RecyclerView.ViewHolder(adapterBinding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
         val binding=UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
         return userList.size
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         holder.adapterBinding.textViewName.text=userList[position].userName
         holder.adapterBinding.textViewAge.text= userList[position].userAge.toString()
         holder.adapterBinding.textViewEmail.text=userList[position].userEmail

         holder.adapterBinding.LinearLayout.setOnClickListener{
             val intent=Intent(context,UpdateUserActivity::class.java)
             intent.putExtra("id",userList[position].userId)
             intent.putExtra("name",userList[position].userName)
             intent.putExtra("age",userList[position].userAge)
             intent.putExtra("email",userList[position].userEmail)

             context.startActivity(intent)
         }
    }

    fun getUserId(position: Int):String{
        return userList[position].userId
    }
}