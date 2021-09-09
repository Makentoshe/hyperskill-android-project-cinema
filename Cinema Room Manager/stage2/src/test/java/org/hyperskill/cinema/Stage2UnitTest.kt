package org.hyperskill.cinema

import android.content.Intent
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class Stage2UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    companion object {
        private const val DOUBLE_ASSERT_DELTA = 0.1
    }

    @Test
    fun testShouldCheckTicketPriceViewExist() {
        val message = "does view with id \"cinema_room_ticket_price\" placed in activity?"
        activity = activityController.setup().get()
        assertNotNull(message, find<TextView>("cinema_room_ticket_price"))
    }

    @Test
    fun testShouldCheckTicketPriceViewDefault() {
        val message = "does default DURATION and RATING properties received from intent valid?"
        activity = activityController.setup().get()
        val ticketPriceView = find<TextView>("cinema_room_ticket_price")

        val value = Scanner(ticketPriceView.text.trim().toString()).findInLine("\\d+\\.\\d+")
        assertEquals(message, 14.22, value.toDouble(), DOUBLE_ASSERT_DELTA)
    }

    @Test
    fun testShouldCheckTicketPriceView1() {
        val message = "does DURATION and RATING properties receives from intent?"

        activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val ticketPriceView = find<TextView>("cinema_room_ticket_price")
        val value = Scanner(ticketPriceView.text.trim().toString()).findInLine("\\d+\\.\\d+")
        assertEquals(message, 16.07, value.toDouble(), DOUBLE_ASSERT_DELTA)
    }

    @Test
    fun testShouldCheckTicketPriceView2() {
        val message = "does DURATION and RATING properties receives from intent?"

        activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 39)
                putExtra("RATING", 3.9f)
            }
        }.setup().get()

        val ticketPriceView = find<TextView>("cinema_room_ticket_price")
        val value = Scanner(ticketPriceView.text.trim().toString()).findInLine("\\d+\\.\\d+")
        assertEquals(message, 10.59, value.toDouble(), DOUBLE_ASSERT_DELTA)
    }

}