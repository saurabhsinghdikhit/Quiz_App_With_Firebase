package com.srb.practical_assignment_3

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
import kotlinx.android.synthetic.main.activity_registration.*

class registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference().child("quiz/user")
        var al= ArrayList<String>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(childNode in dataSnapshot.children) {
                    al.add(childNode.key.toString())
                }
                registxtid.setText((al[al.size-1].toInt()+1).toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        btnregis.setOnClickListener(View.OnClickListener {
            if (!registxtnm.text.toString().equals("")) {
                if (!registxtemail.text.toString().equals("")) {
                    if (!registxtpwd.text.toString().equals("")) {
                        if (!registxtconpwd.text.toString().equals("")) {
                            if (registxtpwd.text.toString().equals(registxtconpwd.text.toString())) {
                                var id= registxtid.text.toString().toInt()
                                myRef.child("$id").setValue(userDataClass(id,registxtnm.text.toString(),
                                    registxtemail.text.toString(),
                                    registxtpwd.text.toString())).addOnCompleteListener{
                                    Toast.makeText(this@registration,"Successfull registered",Toast.LENGTH_SHORT).show()
                                    val i = Intent(this@registration,login::class.java)
                                    startActivity(i)
                                }.addOnFailureListener{
                                    Toast.makeText(this@registration,"Failure",Toast.LENGTH_SHORT).show()
                                }


                            } else Toast.makeText(this, "Confirm password required", Toast.LENGTH_SHORT).show()
                        } else Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(this, "user name is required", Toast.LENGTH_SHORT).show()
            }
        })
        btnloginInregis.setOnClickListener(View.OnClickListener {
            val i = Intent(this,login::class.java)
            startActivity(i)

        })
    }
}
