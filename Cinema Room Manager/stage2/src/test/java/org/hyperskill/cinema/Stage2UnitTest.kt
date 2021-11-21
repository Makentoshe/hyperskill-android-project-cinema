package org.hyperskill.cinema

import android.content.Intent
import org.hyperskill.cinema.abstraction.AbstractUnitTest
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class Stage2UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    companion object {
        private const val DOUBLE_ASSERT_DELTA = 0.1
    }

    @Test
    fun `test should check ticket price view exists`() {
        val message = "does view with id \"cinema_room_ticket_price\" placed in activity?"

        activityController.`launch this activity and execute` {
            `price text view exists`(message)
        }
    }

    @Test
    fun `test should check ticket price view with default arguments`() {
        val message = "does default DURATION and RATING properties received from intent valid?"

        activityController.`launch this activity and execute` {
            `price text view`().`text should contain double`(message, 14.22, DOUBLE_ASSERT_DELTA)
        }
    }

    @Test
    fun `test should check ticket price view with best arguments`() {
        val message = "does DURATION and RATING properties receives from intent?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `price text view`().`text should contain double`(message, 16.07, DOUBLE_ASSERT_DELTA)
        }
    }

    @Test
    fun `test should check ticket price view with custom arguments`() {
        val message = "does DURATION and RATING properties receives from intent?"

        activityController.`launch this activity and execute`(arguments = `custom profitable movie`()) {
            `price text view`().`text should contain double`(message, 10.59, DOUBLE_ASSERT_DELTA)
        }
    }

    @Test
    fun `test should check ticket price view contains two digits after dot`() {
        val message = "Make sure you've correctly formatted the ticket price. The price should contain two numbers after the dot."

        activityController.`launch this activity and execute`(arguments = `custom profitable movie`()) {
            `price text view`().`text should`(assertMessage = message) {
                // should be 4 digits in general, like 12.34
                it.filter { char -> char.isDigit() }.count() == 4
            }
        }
    }

    private fun MainActivity.`price text view exists`(assertMessage: String) {
        assertNotNull(assertMessage, findOrNull("cinema_room_ticket_price"))
    }

    private fun `custom profitable movie`() = Intent().apply {
        putExtra("DURATION", 39)
        putExtra("RATING", 3.9f)
    }

}