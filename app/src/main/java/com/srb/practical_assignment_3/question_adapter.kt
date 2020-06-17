package com.srb.practical_assignment_3

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase


class question_adapter(var ctx:Activity,var ctarray:ArrayList<questionDataClass>):RecyclerView.Adapter<question_viewHolder>() {
    val database = FirebaseDatabase.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): question_viewHolder {

        var view=ctx.layoutInflater.inflate(R.layout.list_items,parent,false)
        var vh=question_viewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return ctarray.size
    }

    override fun onBindViewHolder(holder: question_viewHolder, position: Int) {
        holder.txtid.text="Que:"+ctarray[position].q_id.toString()
        holder.txttitle.text=ctarray[position].q_title.toString()
        holder.txtqtype.text="Type:"+ctarray[position].q_type.toString()
        holder.txtop1.text="A:"+ctarray[position].q_op1.toString()
        holder.txtop2.text="B:"+ctarray[position].q_op2.toString()
        holder.txtop3.text="C:"+ctarray[position].q_op3.toString()
        holder.txtop4.text="D:"+ctarray[position].q_op4.toString()
        holder.txtans.text="Ans:"+ctarray[position].q_ans.toString()
        val id=ctarray[position].q_id.toString()
        holder.delete.setOnClickListener(View.OnClickListener {
            database.getReference().child("quiz/questions/$id").removeValue().addOnCompleteListener{
                Toast.makeText(ctx,"Deleted Successfully", Toast.LENGTH_SHORT).show()
            }.addOnCanceledListener {
                Toast.makeText(ctx,"Failure", Toast.LENGTH_SHORT).show()
            }
            notifyDataSetChanged()
        })
        holder.itemView.setOnClickListener(View.OnClickListener {
            var intent= Intent(ctx,update_question::class.java)
            intent.putExtra("id",ctarray[position].q_id.toString())
            ctx.startActivity(intent)
            ctx.finish()
        })
    }
}