package org.hyperskill.project.android.cinemaroommanager

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val rows = 7
    private val seats = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        grid_layout.alignmentMode = GridLayout.ALIGN_BOUNDS
        grid_layout.columnCount = seats

        val inflater = LayoutInflater.from(this)
        for (row in 0 until rows)  {
            for (seat in 0 until seats) {
                val view = inflater.inflate(R.layout.item_grid_layout, grid_layout, false)
                view.findViewById<TextView>(R.id.item_grid_layout_text).text = "${row + 1}.${seat + 1}"
                grid_layout.addView(view)
            }
        }
    }
}