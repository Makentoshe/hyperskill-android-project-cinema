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

        val intent = intent
        val duration = intent.getIntExtra("DURATION", 108)
        val rating = intent.getFloatExtra("RATING", 4.5f)

        val movieDurationProfit = - (duration * duration / 90) + 2 * duration + 90
        val baseTicketPrice = ((rating * movieDurationProfit) / (rows * seats))

        cinema_room_ticket_price.text = getString(R.string.cinema_room_ticket_price, baseTicketPrice)

        cinema_room_places.alignmentMode = GridLayout.ALIGN_BOUNDS
        cinema_room_places.columnCount = seats

        val inflater = LayoutInflater.from(this)
        for (row in 0 until rows)  {
            for (seat in 0 until seats) {
                val view = inflater.inflate(R.layout.item_grid_layout, cinema_room_places, false)
                view.findViewById<TextView>(R.id.cinema_room_place_item_text).text = "${row + 1}.${seat + 1}"
                cinema_room_places.addView(view)
            }
        }
    }
}