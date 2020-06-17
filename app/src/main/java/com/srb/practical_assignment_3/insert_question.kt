package com.srb.practical_assignment_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_insert_question.*

class insert_question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_question)
        var questiontypeArray= arrayOf("English","Logical Resoning","Aptitiude","General Knowledge")
        var aaquestiontypeAdpater=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,questiontypeArray)
        questiondrop.adapter=aaquestiontypeAdpater
        var answerArray= arrayOf("A","B","C","D")
        var aaanswerAdpater=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,answerArray)
        insselectans.adapter=aaanswerAdpater
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference().child("quiz/questions")
        var al= ArrayList<String>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(childNode in dataSnapshot.children) {
                    al.add(childNode.key.toString())

                }
                txtinsquesid.setText((al[al.size-1].toInt()+1).toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        btninsque.setOnClickListener(View.OnClickListener {
            var ans=""
            if(insselectans.selectedItem.toString().equals("A"))
                ans=txtinsquesop1.text.toString()
            if(insselectans.selectedItem.toString().equals("B"))
                ans=txtinsquesop2.text.toString()
            if(insselectans.selectedItem.toString().equals("C"))
                ans=txtinsquesop3.text.toString()
            if(insselectans.selectedItem.toString().equals("D"))
                ans=txtinsquesop4.text.toString()
            val database = FirebaseDatabase.getInstance()
            val myRef =database.getReference("quiz/questions")
            var id=txtinsquesid.text.toString().toInt()
            myRef.child("$id").setValue(questionDataClass(
                id,questiondrop.selectedItem.toString(),
                txtinsquestital.text.toString(),txtinsquesop1.text.toString(),
                txtinsquesop2.text.toString(),txtinsquesop3.text.toString(),
                txtinsquesop4.text.toString(),ans)).addOnCompleteListener{
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this,dashboard::class.java)
            startActivity(i)
        })
    }
}
