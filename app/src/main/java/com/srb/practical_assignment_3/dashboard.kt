package com.srb.practical_assignment_3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dashboard.*

class dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        im_adminlogout.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(applicationContext, im_adminlogout)
            popupMenu.inflate(R.menu.main_menu)
            popupMenu.setOnMenuItemClickListener { item ->
                val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this@dashboard,login::class.java))
                finish()
                true
            }
            popupMenu.show()
        })
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("username", "abc")
        txtadmin.setText("Welcome: "+user.toString().toUpperCase())
        dashboardinsertbtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,insert_question::class.java))
        })
        dashboardshowbtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,show_questions::class.java))
        })
        dashboardupdbtnbyspinner.setOnClickListener( View.OnClickListener {
            startActivity(Intent(this,update_question_by_spinner::class.java))
        })

    }

}
