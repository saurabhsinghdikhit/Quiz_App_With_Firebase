package com.srb.practical_assignment_3

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_exam_screen.*

class exam_screen : AppCompatActivity() {
    var queno=1
    var position=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_screen)
        val cat = intent.getStringExtra("cat")
        val database = FirebaseDatabase.getInstance()
        val myRef =database.getReference("quiz/questions")
        var al=ArrayList<questionDataClass>()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(childNode in dataSnapshot.children) {
                    if(childNode.child("q_type").getValue().toString().trim().equals(cat.toString().trim())) {
                        val value = childNode.getValue(questionDataClass::class.java)
                        al.add(value!!)
                    }
                }
                totque.setText("Total Questions: ${al.size}")
                var totque=al.size-1
                var score=0
                makequestion(queno,al[position])
                btnnext.setOnClickListener {
                    var selRadioId=rdColor.checkedRadioButtonId
                    var selRadio:RadioButton?=findViewById(selRadioId)
                    if(selRadio==null){
                        Toast.makeText(this@exam_screen,"please select any option",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (position < totque) {
                            if(selRadio.text.toString().trim().equals(al[position].q_ans.toString().trim()))
                                score=score+1
                            position = position + 1
                            queno = queno + 1
                            rdColor.clearCheck()
                            makequestion(queno, al[position])

                        } else {
                            if(selRadio.text.toString().trim().equals(al[position].q_ans.toString().trim()))
                                score=score+1
                            var build = AlertDialog.Builder(this@exam_screen)
                            build.setMessage("Want to see the result")
                            build.setTitle("Quiz is Finished")
                            build.setPositiveButton("Yes",
                                DialogInterface.OnClickListener { dialog, which ->
                                    var build1 = AlertDialog.Builder(this@exam_screen)
                                    build1.setMessage("You have scored $score out of ${al.size}")
                                    build1.setTitle("Your Score:-")
                                    build1.setPositiveButton("Okay",
                                        DialogInterface.OnClickListener { dialog, which ->
                                            startActivity(Intent(this@exam_screen,quiz_type_selection::class.java))
                                            finish()
                                        })
                                    build.setCancelable(false)
                                    var dialog=build1.create()
                                    dialog.show()

                                })
                            build.setNegativeButton("No",
                                DialogInterface.OnClickListener { dialog, which ->
                                    Toast.makeText(this@exam_screen, "Quiz is Finished", Toast.LENGTH_SHORT).show()
                                })
                            build.setCancelable(false)
                            var dialog = build.create()
                            dialog.show()
                        }

                    }
                }
                btnprev.setOnClickListener{
                    if(position>0) {
                        position = position - 1
                        queno=queno-1
                        makequestion(queno,al[position])
                    }
                    else {
                        Toast.makeText(this@exam_screen,"Already on 1st question",Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        /*
        rdColor.setOnCheckedChangeListener { group, checkedId ->
            var selRadioId=rdColor.checkedRadioButtonId
            var selRadio:RadioButton?=findViewById(selRadioId)
            if(selRadio?.text.toString().trim().equals(data[position].q_ans.toString().trim()))
                selRadio?.setTextColor(resources.getColor(R.color.green))
            else
                selRadio?.setTextColor(resources.getColor(R.color.red))
        }
        */
    }
    fun makequestion(que:Int,data:questionDataClass){
        question.text="$que: "+data.q_title.toString()
        op1.setText(data.q_op1.toString())
        op2.setText(data.q_op2.toString())
        op3.setText(data.q_op3.toString())
        op4.setText(data.q_op4.toString())
    }
}
