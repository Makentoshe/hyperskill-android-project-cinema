package org.hyperskill.project.android.cinemaroommanager

import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

// Version 1.0
@RunWith(RobolectricTestRunner::class)
class Stage1UnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckScreenExist() {
        val message = "does view with id \"cinema_room_screen_text\" placed in activity?"

        val activity = activityController.setup().get()
        val screenTextView = activity.findViewById<TextView>(R.id.cinema_room_screen_text)
        assertNotNull(message, screenTextView)
    }

    @Test
    fun testShouldCheckScreenText() {
        val message = "does view with id \"cinema_room_screen_text\" contains a \"Screen\" text?"

        val activity = activityController.setup().get()
        val screenTextView = activity.findViewById<TextView>(R.id.cinema_room_screen_text)
        assertEquals(message, "Screen", screenTextView.text)
    }

    @Test
    fun testShouldCheckPlacesExist() {
        val message = "does view with id \"cinema_room_places\" placed in activity?"

        val activity = activityController.setup().get()
        val placesGridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        assertNotNull(message, placesGridLayout)
    }

    @Test
    fun testShouldCheckPlacesColumnsCount() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of columns?"

        val activity = activityController.setup().get()
        val placesGridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        assertEquals(message, 8, placesGridLayout.columnCount)
    }

    @Test
    fun testShouldCheckPlacesRowsCount() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of rows?"

        val activity = activityController.setup().get()
        val placesGridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        assertEquals(message, 7, placesGridLayout.rowCount)
    }

    @Test
    fun testShouldCheckPlacesSeats() {
        val message = "does view with id \"cinema_room_places\" contains a proper seats describe?"

        val activity = activityController.setup().get()
        val placesGridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        placesGridLayout.forEachIndexed { index, seat ->
            val seatRow = index / 8 + 1
            val seatColumn = index % 8 + 1
            val seatText = seat.findViewById<TextView>(R.id.cinema_room_place_item_text).text
            assertEquals(message, "${seatRow}.${seatColumn}", seatText)
        }
    }

}