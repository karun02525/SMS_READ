package com.payo.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.payo.R
import com.payo.main.SMSUtil.getAllSmsFromProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    // Static variable equivalent
    companion object {
        const val MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1
    }

    private val TAG = MainActivity::class.java.simpleName
     var pieEntries = mutableListOf<PieEntry>()



    private val smsReceiver = SmsReceiver()
    private val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS),
                MY_PERMISSIONS_REQUEST_RECEIVE_SMS
            )
        } else {
            read();
            this.registerReceiver(smsReceiver, filter)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(smsReceiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    this.registerReceiver(smsReceiver, filter)
                    read();
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    @SuppressLint("Recycle", "SetTextI18n")
    fun read() {
        val item = getAllSmsFromProvider();
        val data = ArrayList<DataCreditDebitModel>()

        Log.d(TAG, "--------------------------------")
        item.forEach {
            val body = it.message
            if (body.contains("credited ")) {
                data.add(
                    DataCreditDebitModel(
                        it.id,
                        body.getIndianRupee(),
                        "0.0",
                        it.date.convertDate(),
                        "credit"
                    )
                )
            } else if (body.contains("debited ")) {
                data.add(
                    DataCreditDebitModel(
                        it.id,
                        "0.0",
                        body.getIndianRupee(),
                        it.date.convertDate(),
                        "debit"
                    )
                )
            }
        }

        data.forEach {
            Log.d(
                TAG,
                it.id + " => " + it.date + " => " + it.credited + " => " + it.debited + " => " + it.status
            )
        }


        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        data.filter { it.date == todayDate() }
            .forEach {
                Log.d(
                    TAG,
                    it.id + " => " + it.date + " => " + it.credited + " => " + it.debited + " => " + it.status
                )
            }

        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

        val creditSum = data.sumByDouble { it.credited.toDouble() }
        val debitSum = data.sumByDouble { it.debited.toDouble() }
        val total = data.sumByDouble { it.debited.toDouble() + it.credited.toDouble() }
        val cr = creditSum * 100 / total;
        val dr = debitSum * 100 / total;

        Log.d(TAG, "Sum Credit: $creditSum : $cr%" );
        Log.d(TAG, "Sum Debit: $debitSum : $dr% ");


        tv1.text="Total credit: $creditSum"
        tv2.text="Total debit: $debitSum"
        tv3.text="Total: $total"

        val label = listOf("% Credit ","% Debit")
        pieEntries.add(PieEntry(cr.toFloat(), label[0]))
        pieEntries.add(PieEntry(dr.toFloat(), label[1]))

        pieChat()
    }

    private fun pieChat() {

       val pieDataSet = PieDataSet(pieEntries, "Payo")
        val pieData = PieData(pieDataSet)
        pieChart!!.data = pieData
        pieDataSet.setColors(
            ColorTemplate.rgb("#4CAF50"),
            ColorTemplate.rgb("#F44336")
        )
       // pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE;
        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 13f
        pieDataSet.sliceSpace = 5f
    }

private val entries: Unit
    get() {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(2f, 0))
        pieEntries.add(PieEntry(4f, 1))

    }

}
