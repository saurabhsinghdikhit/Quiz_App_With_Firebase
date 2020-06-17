package com.srb.practical_assignment_3

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_update_question.*
import kotlinx.android.synthetic.main.activity_update_question_by_spinner.*

class update_question_by_spinner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_question_by_spinner)
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
                var aaqid=ArrayAdapter<String>(this@update_question_by_spinner,android.R.layout.simple_dropdown_item_1line,al)
                upddropid.adapter=aaqid
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        var questiontypeArray= arrayOf("English","Logical Resoning","Aptitiude","General Knowledge")
        var aaquestiontypeAdpater= ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,questiontypeArray)
          upddroptype.adapter=aaquestiontypeAdpater
        var answerArray= arrayOf("A","B","C","D")
        var aaanswerAdpater=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,answerArray)
        updans.adapter=aaanswerAdpater
        upddropid.onItemSelectedListener=object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var id=p0!!.selectedItem.toString().toInt()
                val database = FirebaseDatabase.getInstance()
                val myRef =database.getReference().child("quiz/questions/$id")
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        upddroptype.setSelection(aaquestiontypeAdpater.getPosition(dataSnapshot.child("q_type").getValue().toString()))
                        updtital.setText(dataSnapshot.child("q_title").getValue().toString())
                        updop1.setText(dataSnapshot.child("q_op1").getValue().toString())
                        updop2.setText(dataSnapshot.child("q_op2").getValue().toString())
                        updop3.setText(dataSnapshot.child("q_op3").getValue().toString())
                        updop4.setText(dataSnapshot.child("q_op4").getValue().toString())
                        var ans=dataSnapshot.child("q_ans").getValue().toString()
                        if(ans.trim().equals(dataSnapshot.child("q_op1").getValue().toString().trim()))
                            updans.setSelection(0)
                        else if(ans.trim().equals(dataSnapshot.child("q_op2").getValue().toString().trim()))
                            updans.setSelection(1)
                        else if(ans.trim().equals(dataSnapshot.child("q_op3").getValue().toString().trim()))
                            updans.setSelection(2)
                        else if(ans.trim().equals(dataSnapshot.child("q_op4").getValue().toString().trim()))
                            updans.setSelection(3)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                    }
                })

            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        updbtnquebyid.setOnClickListener{
            var build= AlertDialog.Builder(this)
            build.setMessage("Are you sure to update!!")
            build.setTitle("Update Record")
            build.setPositiveButton("Update",
                DialogInterface.OnClickListener { dialog, which ->
                    var ans=""
                    if(updans.selectedItem.toString().equals("A"))
                        ans=updop1.text.toString()
                    if(updans.selectedItem.toString().equals("B"))
                        ans=updop2.text.toString()
                    if(updans.selectedItem.toString().equals("C"))
                        ans=updop3.text.toString()
                    if(updans.selectedItem.toString().equals("D"))
                        ans=updop4.text.toString()
                    var question=questionDataClass(upddropid.selectedItem.toString().toInt(),
                        upddroptype.selectedItem.toString(),
                        updtital.text.toString(),
                        updop1.text.toString(),
                        updop2.text.toString(),
                        updop3.text.toString(),
                        updop4.text.toString(),
                        ans)
                    var id=upddropid.selectedItem.toString().toInt()
                    database.getReference().child("quiz/questions/$id").setValue(question).addOnCompleteListener{
                        Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Failure", Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this,show_questions::class.java))
                    finish()
                })
            build.setNegativeButton("Cancel",DialogInterface.OnClickListener{dialog, which ->
                Toast.makeText(this,"cancel!!",Toast.LENGTH_LONG).show()

            })
            build.setCancelable(false)
            var dialog=build.create()
            //display dialogbox
            dialog.show()
        }
    }


}
