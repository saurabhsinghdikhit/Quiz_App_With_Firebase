package com.srb.practical_assignment_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnlogin.setOnClickListener {
            var nm= txtnm.text.toString()
            var pwd= txtpwd.text.toString()
            if(nm.equals("admin")&& pwd.equals("admin")){
                val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", "admin")
                editor.apply()
                val i = Intent(this, dashboard::class.java)
                startActivity(i)
                finish()
            }else {
                val database = FirebaseDatabase.getInstance()
                val myRef =database.getReference("quiz/user")
                var al=""
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (childNode in dataSnapshot.children) {
                            if (childNode.child("user_email").getValue().toString().trim().equals(nm.toString().trim())
                                && childNode.child("password").getValue().toString().trim().equals(pwd.toString().trim())) {
                                val name = childNode.child("user_name").getValue().toString()
                                al=name!!
                            }
                        }
                        if(!al.equals("")) {
                            val sharedPreferences =
                                getSharedPreferences("login", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("username", al.toString())
                            editor.apply()
                            val i = Intent(this@login, quiz_type_selection::class.java)
                            startActivity(i)
                            finish()
                        }
                        else
                            Toast.makeText(this@login,"Invalid Credientials",Toast.LENGTH_SHORT).show()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                    }
                })

                }


            }
        btnegisinlogin.setOnClickListener(View.OnClickListener {
            val i = Intent(this, registration::class.java)
            startActivity(i)

        })
    }
}
