/*
package com.payo.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

class MainActivity : AppCompatActivity() {
    var pieChart: PieChart? = null
    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    var pieEntries: ArrayList<*>? = null
    var PieEntryLabels: ArrayList<*>? = null
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pieChart = findViewById(R.id.pieChart)
        entries
        pieDataSet = PieDataSet(pieEntries, "")
        pieData = PieData(pieDataSet)
        pieChart!!.data = pieData
        pieDataSet!!.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet!!.sliceSpace = 2f
        pieDataSet!!.valueTextColor = Color.WHITE
        pieDataSet!!.valueTextSize = 10f
        pieDataSet!!.sliceSpace = 5f
    }

    private val entries: Unit
        private get() {
            pieEntries = ArrayList<Any?>()
            pieEntries.add(PieEntry(2f, 0))
            pieEntries.add(PieEntry(4f, 1))
            pieEntries.add(PieEntry(6f, 2))
            pieEntries.add(PieEntry(8f, 3))
            pieEntries.add(PieEntry(7f, 4))
            pieEntries.add(PieEntry(3f, 5))
        }
}*/
