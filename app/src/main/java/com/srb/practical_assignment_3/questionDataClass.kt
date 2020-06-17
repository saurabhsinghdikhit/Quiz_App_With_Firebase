package com.srb.practical_assignment_3

    data class questionDataClass ( var q_id:Int,
                               var q_type:String,
                               var q_title:String,
                               var q_op1:String,
                               var q_op2:String,
                               var q_op3:String,
                               var q_op4:String,
                               var q_ans:String) {
    constructor():this(0,"","","" ,"","","","" )
}