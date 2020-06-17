package com.srb.practical_assignment_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_questions.*

class show_questions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_questions)
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("username", "abc")
        txtadmindash.setText("Welcome: "+user.toString().toUpperCase())
        im_admindashlogout.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(applicationContext, im_admindashlogout)
            popupMenu.inflate(R.menu.main_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this@show_questions,login::class.java))
                finish()
                true
            }
            popupMenu.show()
        })
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("quiz/questions")
        var al=ArrayList<questionDataClass>()
        var lm=StaggeredGridLayoutManager(1,RecyclerView.VERTICAL)
        recyclerView.layoutManager=lm
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var ad=question_adapter(this@show_questions,al)
                for(childNode in dataSnapshot.children) {
                    val value = childNode.getValue(questionDataClass::class.java)
                    al.add(value!!)
                    ad.notifyDataSetChanged()

                }
                if(ad.itemCount>0)
                    progressBar.visibility=View.GONE
                recyclerView.adapter=ad

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })


    }
}

