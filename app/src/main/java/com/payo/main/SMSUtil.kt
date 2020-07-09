package com.payo.main

import android.content.ContentResolver
import android.content.Context
import android.provider.Telephony
import kotlin.collections.ArrayList

object SMSUtil {

    private var totalSMSCount = 0
    private lateinit var sms: SMS

    fun Context.getAllSmsFromProvider(): List<SMS> {
        val lstSms: MutableList<SMS> = ArrayList()
        val cr: ContentResolver = this.contentResolver
        cr.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.READ,
                Telephony.Sms.DATE,
                Telephony.Sms.TYPE
            ),
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )?.let { cursor ->
            totalSMSCount = cursor.count
            if (cursor.moveToFirst()) {
                for (i in 0 until totalSMSCount) {
                    val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                    sms = SMS(
                        id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                        address = cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        message = cursor.getString(cursor.getColumnIndexOrThrow("body")),
                        readState = cursor.getString(cursor.getColumnIndex("read")),
                        date = date,
                        folderName = if (cursor.getString(cursor.getColumnIndexOrThrow("type"))
                                .contains(Constants.FLAG_TYPE_INBOX)
                        ) {
                            Constants.FOLDER_TYPE_INBOX
                        } else {
                            Constants.FOLDER_TYPE_SENT
                        }
                    )
                    lstSms.add(sms)
                    cursor.moveToNext()
                }
            }
            cursor.close()
        }
        return lstSms
    }
}