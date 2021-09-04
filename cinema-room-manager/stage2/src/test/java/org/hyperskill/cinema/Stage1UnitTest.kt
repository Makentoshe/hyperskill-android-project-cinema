package org.hyperskill.cinema

import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.core.view.size
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Version 04.09.2021
@RunWith(RobolectricTestRunner::class)
class Stage1UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Before
    fun before() {
        activity = activityController.setup().get()
    }

    @Test
    fun testShouldCheckScreenExist() {
        val message = "does view with id \"cinema_room_screen_text\" placed in activity?"
        assertNotNull(message, find<TextView>("cinema_room_screen_text"))
    }

    @Test
    fun testShouldCheckScreenText() {
        val message = "does view with id \"cinema_room_screen_text\" contains a \"Screen\" text?"
        assertEquals(message, "Screen", find<TextView>("cinema_room_screen_text").text)
    }

    @Test
    fun testShouldCheckPlacesExist() {
        val message = "does view with id \"cinema_room_places\" placed in activity?"
        assertNotNull(message, find<GridLayout>("cinema_room_places"))
    }

    @Test
    fun testShouldCheckPlacesColumnsCount() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of columns?"
        assertEquals(message, 8, find<GridLayout>("cinema_room_places").columnCount)
    }

    @Test
    fun testShouldCheckPlacesRowsCount() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of rows?"
        assertEquals(message, 7, find<GridLayout>("cinema_room_places").rowCount)
    }

    @Test
    fun testShouldCheckPlacesSeats() {
        val message = "does view with id \"cinema_room_places\" contains a proper seats describe?"

        find<GridLayout>("cinema_room_places").also { gridLayout ->
            assertEquals(56, gridLayout.size)
        }.forEachIndexed { index, seat ->
            val seatRow = index / 8 + 1
            val seatColumn = index % 8 + 1

            // Note: even seat is can be already a view, it shouldn't be casted to textView
            // because someone might try to wrap a TextView with other container such
            // as FrameLayout or CardLayout
            // In this case the different from `(seat as TextView).text` solution will not
            // being passed.
            val seatText = seat.find<TextView>("cinema_room_place_item_text").text
            assertEquals(message, "${seatRow}.${seatColumn}", seatText)
        }
    }

}