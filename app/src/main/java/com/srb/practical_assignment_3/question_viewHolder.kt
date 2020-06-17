package com.srb.practical_assignment_3

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_items.view.*

class question_viewHolder(view:View):RecyclerView.ViewHolder(view) {
    var txtid=view.textqueid
    var txttitle=view.textquetit
    var txtqtype=view.textqtype
    var txtop1=view.textop1
    var txtop2=view.textop2
    var txtop3=view.textop3
    var txtop4=view.textop4
    var txtans=view.textans
    var delete=view.delquestion

}