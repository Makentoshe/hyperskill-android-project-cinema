package org.hyperskill.cinema

import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import androidx.core.view.size
import org.hyperskill.cinema.abstraction.AbstractUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Version 21.09.2021
@RunWith(RobolectricTestRunner::class)
class Stage1UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun `test should check screen view exists`() {
        val message = "does view with id \"cinema_room_screen_text\" placed in activity?"

        activityController.`launch this activity and execute` {
            assertNotNull(message, find<TextView>("cinema_room_screen_text"))
        }
    }

    @Test
    fun `test should check screen view text`() {
        val message = "does view with id \"cinema_room_screen_text\" contains a \"Screen\" text?"

        activityController.`launch this activity and execute` {
            `screen text view`().`text should be`(errorMessage = message, "Screen")
        }
    }

    @Test
    fun `test should check places exists`() {
        val message = "does view with id \"cinema_room_places\" placed in activity?"

        activityController.`launch this activity and execute` {
            assertNotNull(message, find<GridLayout>("cinema_room_places"))
        }
    }

    @Test
    fun `test should check places columns count`() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of columns?"

        activityController.`launch this activity and execute` {
            assertEquals(message, 8, find<GridLayout>("cinema_room_places").columnCount)
        }
    }

    @Test
    fun `test should check places rows count`() {
        val message = "does view with id \"cinema_room_places\" contains a proper count of rows?"

        activityController.`launch this activity and execute` {
            assertEquals(message, 7, find<GridLayout>("cinema_room_places").rowCount)
        }
    }

    @Test
    fun `test should check places seats`() {
        val message = "does view with id \"cinema_room_places\" contains a proper seats describe?"

        activityController.`launch this activity and execute` {
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

}