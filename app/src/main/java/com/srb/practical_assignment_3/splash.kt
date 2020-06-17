package com.srb.practical_assignment_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        linearLayout.visibility= View.VISIBLE
        val aniFadein = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        linearLayout.animation=aniFadein
        Handler().postDelayed(Runnable {
            val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val user = sharedPreferences.getString("username", "abc")

            if(user.equals("abc")){

                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()

            }else if(user.equals("admin")){

                val intent = Intent(this, dashboard::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, quiz_type_selection::class.java)
                startActivity(intent)
                finish()
            }


        }, 2500)
    }
}
