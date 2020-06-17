package com.srb.practical_assignment_3

data class userDataClass ( var user_id:Int,
                               var user_name:String,
                               var user_email:String,
                               var password:String) {
    constructor():this(0,"","","" )
}