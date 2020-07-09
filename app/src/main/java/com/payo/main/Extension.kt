package com.payo.main

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


 fun String.getIndianRupee():String {
    val p: Pattern = Pattern.compile("[Rs|IN][Rs\\s|IN.](\\d+[.](\\d\\d|\\d))")
    val m = p.matcher(this)
    while (m.find()) {
        return  m.group(1)!!
    }
    return ""
}

 fun String.convertDate(): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.timeInMillis = this.toLong()
    return DateFormat.format("dd-MM-yyyy", cal).toString()
}

@SuppressLint("SimpleDateFormat")
 fun todayDate(): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    return sdf.format(Date())
}