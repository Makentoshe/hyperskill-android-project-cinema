package org.hyperskill.project.android.cinemaroommanager

import android.content.Intent
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.forEachIndexed
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

// Version 1.0
@RunWith(RobolectricTestRunner::class)
class Stage3UnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckToast1() {
        toastCheck(90, 5f, 0, 0, 24.11f)
    }

    @Test
    fun testShouldCheckToast2() {
        toastCheck(34, 2.8f, 5, 3, 5.74f)
    }

    @Test
    fun testShouldCheckToast3() {
        toastCheck(108, 4.5f, 5, 6, 11.18f)
    }

    private fun toastCheck(duration: Int, rating: Float, row: Int, place: Int, price: Float) {
        val message = """
            do you properly calculates a ticket price? 
            For ${rating}f rating and $duration duration the ticket price 
            in ${row + 1} row and in ${place + 1} place should be $price$
        """.trimMargin()

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", duration)
                putExtra("RATING", rating)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        gridLayout.getChildAt(row * gridLayout.columnCount + place).performClick()
        println(row * gridLayout.columnCount + place)

        assertTrue(message, ShadowToast.showedToast("$price$"))
    }
}