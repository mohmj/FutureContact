package com.example.futurecontact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.futurecontact.Adapters.MessageFromUserAdapter
import com.example.futurecontact.Adapters.MessageToUserAdapter
import com.example.futurecontact.Models.ContactMessage
import com.example.futurecontact.Models.UserInformation
import com.example.futurecontact.Services.Database
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        var uid=Firebase.auth.uid
        var adapter=GroupAdapter<ViewHolder>()
        chat_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        var userId=intent.getParcelableExtra<UserInformation>(Database().userInformation)?.uid
        var userEmail= intent.getParcelableExtra<UserInformation>(Database().userInformation)?.email
        var userName=intent.getParcelableExtra<UserInformation>(Database().userInformation)?.name
        chat_activity_uid_text_view.text="User ID: $userId"
        chat_activity_email_text_view.text="User email: $userEmail"
        chat_activity_name_text_view.text="User name: $userName"

        var reference= Firebase.database.getReference("chats/chat_messages/$userId/messages")
        var userChatReference=Firebase.database.getReference("users/$userId/chat/messages")

        reference.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var message=snapshot.getValue(ContactMessage::class.java)
                if(message != null){
                    if(message.uid==uid.toString()){
                        adapter.add(MessageToUserAdapter(message))
                    }else{
                        adapter.add(MessageFromUserAdapter(message))
                    }
                }
                chat_activity_recycler_view.adapter=adapter
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var message=snapshot.getValue(ContactMessage::class.java)
                if(message != null){
                    if(message.uid==uid.toString()){
                        adapter.add(MessageToUserAdapter(message))
                    }else{
                        adapter.add(MessageFromUserAdapter(message))
                    }
                }
                chat_activity_recycler_view.adapter=adapter
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })

        chat_activity_send_button.setOnClickListener(){
            val message=chat_activity_message_edit_text.text
            if(message != null){
                reference.push().setValue(ContactMessage(uid.toString(),message.toString()))
                userChatReference.push().setValue(ContactMessage(uid.toString(),message.toString()))
                chat_activity_message_edit_text.setText("")
            }
        }
    }
}