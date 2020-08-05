package com.example.futurecontact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var auth= Firebase.auth
        var uid=auth.uid

        login_activity_button.setOnClickListener(){
            var email=login_activity_email_edit_text.text.toString()
            var password=login_activity_password_edit_text.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    var intetn= Intent(this,MainActivity::class.java)
                    intetn.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intetn)
                }.addOnFailureListener {
                    Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Please fill the data", Toast.LENGTH_LONG).show()
            }
        }
    }
}