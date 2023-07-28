package com.avi.firebase

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avi.firebase.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.values

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    val database:FirebaseDatabase= FirebaseDatabase.getInstance()
    val myReference:DatabaseReference=database.reference.child("MyUsers")

    //162
    val userList=ArrayList<Users>()
    lateinit var userAdapter: UsersAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        val view=mainBinding.root
        setContentView(view)

        mainBinding.floatingActionButton.setOnClickListener {

            val intent = Intent(this,AddUserActivity::class.java)
            startActivity(intent)

        }
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean
            {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val id= userAdapter.getUserId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                Toast.makeText(applicationContext,"The user was deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(mainBinding.recyclerView)
        retrieveDataFromDataBase()
    }

    fun retrieveDataFromDataBase(){

        myReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                 for(eachUser in snapshot.children){
                     val user=eachUser.getValue(Users::class.java)
                     if (user!=null){
                         println("userId:${user.userId}")
                         println("userName${user.userName}")
                         println("userAge:${user.userAge}")
                         println("userEmail:${user.userEmail}")
                         println("*************************")
                         //162
                         userList.add(user)
                     }
                     userAdapter= UsersAdapter(this@MainActivity,userList)
                     mainBinding.recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
                     mainBinding.recyclerView.adapter=userAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteAll){
            showDialogMessage()
        }
        return super.onOptionsItemSelected(item)

    }

    fun showDialogMessage(){
        val dialogMessage=AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Users")
        dialogMessage.setMessage("If click yes, all user will be deleted,"+
                "if you want to delete a specific user, you can swipe the item you want to delete right or left 👌😍😍😍")
        dialogMessage.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialogMessage.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            myReference.removeValue().addOnCompleteListener {task->

                if(task.isSuccessful){
                   userAdapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext,"All user were deleted ",Toast.LENGTH_SHORT).show()
                }
            }
        })
        dialogMessage.create().show()
    }
}