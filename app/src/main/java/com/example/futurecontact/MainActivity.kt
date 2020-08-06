package com.example.futurecontact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurecontact.Adapters.ContactShowAdapter
import com.example.futurecontact.Models.UserContactId
import com.example.futurecontact.Models.UserInformation
import com.example.futurecontact.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()

        fecthContacts()

        main_activity_logout_constraint_layout.setOnClickListener(){
            Firebase.auth.signOut()
            checkLogin()
        }



    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
            }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        when(id){
            R.id.main_menu_refresh ->{
                Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()
                fecthContacts()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun fecthContacts(){
        var adapter=GroupAdapter<ViewHolder>()

        var reference=Firebase.database.getReference("chats/chat_users")
        main_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        var uid=Firebase.auth.uid.toString()
        reference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var user=it.getValue(UserInformation::class.java)
                    if(user != null){
                        if(user.uid != uid){
                            adapter.add(ContactShowAdapter(user))
                        }
                    }
                }
                main_activity_recycler_view.adapter=adapter

                adapter.setOnItemClickListener { item, view ->
                    val intent= Intent(view.context,ChatActivity::class.java)
                    val user=item as ContactShowAdapter
                    intent.putExtra(Database().userInformation,user.user)
                    startActivity(intent)
                }


            }

        })
    }

    fun checkLogin() {
        var uid = Firebase.auth.uid
        if (uid == null) {
            var intetn = Intent(this, LoginActivity::class.java)
            intetn.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intetn)
        }
    }
}