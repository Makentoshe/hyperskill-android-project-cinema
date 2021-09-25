package org.hyperskill.cinema

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_grid_layout.view.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private val rows = 7
    private val seats = 8

    private val purchasedTickets = HashSet<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val duration = intent.getIntExtra("DURATION", 108)
        val rating = intent.getFloatExtra("RATING", 4.5f)

        val movieDurationProfit = -(duration * duration / 90) + 2 * duration + 90
        val baseTicketPrice = ((rating * movieDurationProfit) / (rows * seats))
        val calculatedTicketCosts = getCalculatedTicketCosts(baseTicketPrice)

        cinema_room_ticket_price.text = getString(R.string.cinema_room_ticket_price, baseTicketPrice)

        cinema_room_places.alignmentMode = GridLayout.ALIGN_BOUNDS
        cinema_room_places.columnCount = seats

        var totalIncome = 0f
        val inflater = LayoutInflater.from(this)
        for (row in 0 until rows) {
            for (seat in 0 until seats) {
                totalIncome += calculatedTicketCosts[row]
                val view = inflater.inflate(R.layout.item_grid_layout, cinema_room_places, false)
                view.findViewById<TextView>(R.id.cinema_room_place_item_text).text = "${row + 1}.${seat + 1}"
                view.setOnClickListener {
                    if (purchasedTickets.contains(ticketCompare(row, seat))) return@setOnClickListener

                    CustomDialogFragment.show(
                        supportFragmentManager, "Sas", calculatedTicketCosts[row], row, seat
                    )
                }
                cinema_room_places.addView(view)
            }
        }

        cinema_room_total_income.text = getString(R.string.cinema_room_total_income, totalIncome)
        cinema_room_current_income.text = getString(R.string.cinema_room_current_income, 0f)
        cinema_room_available_seats.text = getString(R.string.cinema_room_available_seats, rows * seats)
        cinema_room_occupied_seats.text = getString(R.string.cinema_room_occupied_seats, 0)
    }

    private fun getCalculatedTicketCosts(baseTicketCost: Float): List<Float> {
        val minTicketCost = baseTicketCost * 0.5f
        val maxTicketCost = baseTicketCost * 1.5f
        val stepTicketCost = (maxTicketCost - minTicketCost) / rows

        val calculatedTicketCosts = ArrayList<Float>()
        var tempTicketCost = maxTicketCost
        while (tempTicketCost >= minTicketCost) {
            calculatedTicketCosts.add(tempTicketCost)
            tempTicketCost -= stepTicketCost
        }

        return calculatedTicketCosts
    }

    internal fun markSeatAsPurchased(row: Int, seat: Int, price: Float) {
        purchasedTickets.add(Ticket(row, seat, price))
        val view = cinema_room_places.getChildAt(row * cinema_room_places.columnCount + seat)
        view.cinema_room_place_indicator.setCardBackgroundColor(Color.DKGRAY)
        val currentTotalIncome = purchasedTickets.map { it.price }.reduce { acc, price -> acc + price }
        cinema_room_current_income.text = getString(R.string.cinema_room_current_income, currentTotalIncome)
        cinema_room_occupied_seats.text = getString(R.string.cinema_room_occupied_seats, purchasedTickets.size)
        cinema_room_available_seats.text = getString(R.string.cinema_room_available_seats, rows * seats - purchasedTickets.size)
    }
}

class CustomDialogFragment : DialogFragment() {

    companion object {
        fun show(
            fragmentManager: FragmentManager, tag: String, ticketCost: Float, row: Int, seat: Int
        ) = CustomDialogFragment().apply {
            arguments = Bundle().apply {
                putFloat("TicketCost", ticketCost)
                putInt("Row", row)
                putInt("Seat", seat)
            }
        }.show(fragmentManager, tag)
    }

    private val cost: Float
        get() = arguments!!.getFloat("TicketCost")

    private val row: Int
        get() = arguments!!.getInt("Row")

    private val seat: Int
        get() = arguments!!.getInt("Seat")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Buy a ticket ${row + 1} row ${seat + 1} place")
        builder.setMessage("Your ticket price is ${round(cost * 100) / 100}$")
        builder.setPositiveButton("Buy a ticket") { dialog, which ->
            (requireActivity() as MainActivity).markSeatAsPurchased(row, seat, cost)
        }
        builder.setNegativeButton("Cancel purchase") { dialog, which ->
            dialog.dismiss()
        }
        return builder.create()
    }
}

data class Ticket(val row: Int, val place: Int, val price: Float) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        if (row != other.row) return false
        if (place != other.place) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + place
        return result
    }
}

fun ticketCompare(row: Int, seat: Int) = Ticket(row, seat, -1f)
