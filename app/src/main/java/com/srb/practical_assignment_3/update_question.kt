package com.srb.practical_assignment_3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_update_question.*

class update_question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_question)
        var questiontypeArray= arrayOf("English","Logical Resoning","Aptitiude","General Knowledge")
        var aaquestiontypeAdpater= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,questiontypeArray)
        updquestiondrop.adapter=aaquestiontypeAdpater
        var answerArray= arrayOf("A","B","C","D")
        var aaanswerAdpater=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,answerArray)
        updinsselectans.adapter=aaanswerAdpater
        val id = intent.getStringExtra("id")
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference().child("quiz/questions/$id")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                updquestiondrop.setSelection(aaquestiontypeAdpater.getPosition(dataSnapshot.child("q_type").getValue().toString()))
                updtxtinsquesid.setText(dataSnapshot.child("q_id").getValue().toString())
                updtxtinsquestital.setText(dataSnapshot.child("q_title").getValue().toString())
                updtxtinsquesop1.setText(dataSnapshot.child("q_op1").getValue().toString())
                updtxtinsquesop2.setText(dataSnapshot.child("q_op2").getValue().toString())
                updtxtinsquesop3.setText(dataSnapshot.child("q_op3").getValue().toString())
                updtxtinsquesop4.setText(dataSnapshot.child("q_op4").getValue().toString())
                var ans=dataSnapshot.child("q_ans").getValue().toString()
                if(ans.trim().equals(dataSnapshot.child("q_op1").getValue().toString().trim()))
                    updinsselectans.setSelection(0)
                else if(ans.trim().equals(dataSnapshot.child("q_op2").getValue().toString().trim()))
                    updinsselectans.setSelection(1)
                else if(ans.trim().equals(dataSnapshot.child("q_op3").getValue().toString().trim()))
                    updinsselectans.setSelection(2)
                else if(ans.trim().equals(dataSnapshot.child("q_op4").getValue().toString().trim()))
                    updinsselectans.setSelection(3)

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        Toast.makeText(this,"str",Toast.LENGTH_SHORT)
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        updbtnque.setOnClickListener(View.OnClickListener {
            var ans = ""
            if (updinsselectans.selectedItem.toString().equals("A"))
                ans = updtxtinsquesop1.text.toString()
            if (updinsselectans.selectedItem.toString().equals("B"))
                ans = updtxtinsquesop2.text.toString()
            if (updinsselectans.selectedItem.toString().equals("C"))
                ans = updtxtinsquesop3.text.toString()
            if (updinsselectans.selectedItem.toString().equals("D"))
                ans = updtxtinsquesop4.text.toString()
            var question = questionDataClass(
                id.toInt(),
                updquestiondrop.selectedItem.toString(),
                updtxtinsquestital.text.toString(),
                updtxtinsquesop1.text.toString(),
                updtxtinsquesop2.text.toString(),
                updtxtinsquesop3.text.toString(),
                updtxtinsquesop4.text.toString(),
                ans
            )
            database.getReference().child("quiz/questions/$id").setValue(question).addOnCompleteListener{
                Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this,show_questions::class.java))
            finish()

        })
    }
}
