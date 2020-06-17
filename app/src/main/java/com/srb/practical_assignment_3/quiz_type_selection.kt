package com.srb.practical_assignment_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quiz_type_selection.*

class quiz_type_selection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_type_selection)
        im_userlogout.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(applicationContext, im_userlogout)
            popupMenu.inflate(R.menu.main_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this@quiz_type_selection,login::class.java))
                finish()
                true
            }
            popupMenu.show()
        })
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("username", "abc")
      //  val userid = sharedPreferences.getString("user_id", "abc")
        txtuser.setText("Welcome: "+user.toString().toUpperCase())
        var ar=ArrayList<String>()
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("quiz/questions")
        var al=ArrayList<String>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(childNode in dataSnapshot.children) {
                    val value = childNode.child("q_type").getValue().toString()
                    al.add(value!!)

                }
                if(al.size>0)
                    progressBarquiz.visibility=View.GONE
                var aaquestiontypeAdpater=ArrayAdapter<String>(this@quiz_type_selection,android.R.layout.simple_dropdown_item_1line,al.distinct())
                questiontypedrop.adapter=aaquestiontypeAdpater


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        //   var aaqid= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr)
     //   questiontypedrop.adapter=aaqid
        startquizbtn.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,exam_screen::class.java)
            intent.putExtra("cat",questiontypedrop.selectedItem.toString())
            startActivity(intent)
        })
    }
}
