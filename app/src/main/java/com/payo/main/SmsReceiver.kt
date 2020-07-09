package com.payo.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log


class SmsReceiver : BroadcastReceiver() {

    val TAG = SmsReceiver::class.java.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        val intentAction = intent.action

        if (intentAction != null) {
            var smsSender = ""
            var smsBody = ""
            when (intentAction) {
                Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                    Log.i(TAG, "SMS Received")
                    val smsBundle = intent.extras

                    if (smsBundle != null) {
                        val pdus = smsBundle["pdus"] as? Array<*>
                        if (pdus == null) { // Display some error to the user
                            Log.e(TAG, "SmsBundle had no pdus key")
                            return
                        }
                        val messages: Array<SmsMessage?> = arrayOfNulls(pdus.size)
                        for (i in messages.indices) {
                            messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                            smsBody += messages[i]?.messageBody
                        }
                        smsSender = messages[0]?.originatingAddress ?: ""
                    }

                    Log.i(TAG, "smsSender: $smsSender")
                    Log.i(TAG, "smsBody: $smsBody")
                }
            }
        }
    }
/*


    @SuppressLint("NewApi")
    fun getAllSms(): List<String>? {
        val lstSms: MutableList<String> = ArrayList()
        val cr: ContentResolver = getContentResolver()
        val c: Cursor? = cr.query(Telephony.Sms.Inbox.CONTENT_URI, arrayOf(Telephony.Sms.Inbox.BODY),  // Select body text null, null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        ) // Default
        // sort
        // order);
        val totalSMS: Int = c!!.getCount()
        if (c.moveToFirst()) {
            for (i in 0 until totalSMS) {
                lstSms.add(c.getString(0))
                c.moveToNext()
            }
        } else {
            throw RuntimeException("You have no SMS in Inbox")
        }
        c.close()
        return lstSms
    }

*/


}
