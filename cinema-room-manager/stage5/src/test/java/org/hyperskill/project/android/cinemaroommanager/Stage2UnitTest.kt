package org.hyperskill.project.android.cinemaroommanager

import android.content.Intent
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

// Version 1.0
@RunWith(RobolectricTestRunner::class)
class Stage2UnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckTicketPriceViewExist() {
        val message = "does view with id \"cinema_room_ticket_price\" placed in activity?"

        val activity = activityController.setup().get()
        val ticketPriceView = activity.findViewById<TextView?>(R.id.cinema_room_ticket_price)
        assertNotNull(message, ticketPriceView)
    }

    @Test
    fun testShouldCheckTicketPriceViewDefault() {
        val message = "does default DURATION and RATING properties received from intent valid?"

        val activity = activityController.setup().get()
        val ticketPriceView = activity.findViewById<TextView?>(R.id.cinema_room_ticket_price)
        assertEquals(message, "Estimated ticket price: 14.22\$", ticketPriceView.text.trim())
    }

    @Test
    fun testShouldCheckTicketPriceView1() {
        val message = "does DURATION and RATING properties receives from intent?"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()
        val ticketPriceView = activity.findViewById<TextView?>(R.id.cinema_room_ticket_price)
        assertEquals(message, "Estimated ticket price: 16.07\$", ticketPriceView.text.trim())
    }

    @Test
    fun testShouldCheckTicketPriceView2() {
        val message = "does DURATION and RATING properties receives from intent?"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 39)
                putExtra("RATING", 3.9f)
            }
        }.setup().get()
        val ticketPriceView = activity.findViewById<TextView?>(R.id.cinema_room_ticket_price)
        assertEquals(message, "Estimated ticket price: 10.59\$", ticketPriceView.text.trim())
    }

}