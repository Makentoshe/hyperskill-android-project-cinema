package org.hyperskill.cinema

import android.content.Intent
import org.hyperskill.cinema.abstraction.AbstractUnitTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

// Version 21.11.2021
@RunWith(RobolectricTestRunner::class)
class Stage3UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun `test should check toast with best arguments`() {
        `test toast with parameters`(duration = 90, rating = 5f, row = 0, place = 0, price = 24.11f)
    }

    @Test
    fun `test should check toast with bad arguments`() {
        `test toast with parameters`(duration = 34, rating = 2.8f, row = 5, place = 3, price = 5.74f)
    }

    @Test
    fun `test should check toast with default arguments`() {
        `test toast with parameters`(duration = 108, rating = 4.5f, row = 5, place = 6, price = 11.18f)
    }

    private fun `test toast with parameters`(duration: Int, rating: Float, row: Int, place: Int, price: Float) {
        val message = """
            have you calculated a ticket price properly? 
            For ${rating}f rating and $duration duration the ticket price 
            in ${row + 1} row and in ${place + 1} place should be $price$
        """.trimMargin()

        val arguments = Intent().apply {
            putExtra("DURATION", duration)
            putExtra("RATING", rating)
        }

        activityController.`launch this activity and execute`(arguments = arguments) {
            `grid layout child` { gridLayout -> row * gridLayout.columnCount + place }.`perform click`()
            `text in toast should contain double`(message, value = price.toDouble())
        }
    }

    private fun `text in toast should contain double`(assertMessage: String, value: Double) {
        val bool = ShadowToast.getTextOfLatestToast().`is contain double`(value, `with delta` = 0.1)
        assertTrue(assertMessage, bool)
    }
}