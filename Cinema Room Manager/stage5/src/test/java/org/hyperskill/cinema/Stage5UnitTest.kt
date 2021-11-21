package org.hyperskill.cinema

import android.widget.TextView
import org.hyperskill.cinema.abstraction.AbstractUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Version 21.11.2021
@RunWith(RobolectricTestRunner::class)
class Stage5UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun `test should check total cinema income value for most profitable movie`() {
        val message = "did you calculate total income properly?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `for total income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Total cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(964.29, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check total cinema income value for default profitable movie`() {
        val message = "did you calculate total income properly?"

        activityController.`launch this activity and execute`(arguments = `default profitable movie`()) {
            `for total income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Total cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(853.39, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check initial current cinema income value for most profitable movie`() {
        val message = "did you calculate current income properly?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `for current income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Current cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(0.0, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check initial current cinema income value for default profitable movie`() {
        val message = "did you calculate current income properly?"

        activityController.`launch this activity and execute`(arguments = `default profitable movie`()) {
            `for current income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Current cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(0.0, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check current cinema income value for most profitable movie`() {
        val message = "did you calculate current income properly?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`().`perform click`()
            `for current income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Current cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(24.11, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check current cinema income value for default profitable movie`() {
        val message = "did you calculate current income properly?"

        activityController.`launch this activity and execute`(arguments = `default profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`().`perform click`()
            `for current income view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Current cinema income: ")
                val endsWith = text.endsWith("\$")
                val containsDouble = text.`is contain double`(21.33, 0.1)
                return@`text should be` startsWith && endsWith && containsDouble
            }
        }
    }

    @Test
    fun `test should check initial available seats value`() {
        val message = "did you really count an available seats?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `for available seats view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Available seats: ")
                val containsInteger = text.`is contain integer`(56)
                return@`text should be` startsWith && containsInteger
            }
        }
    }

    @Test
    fun `test should check available seats value`() {
        val message = "did you really count an available seats?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`().`perform click`()
            `for available seats view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Available seats: ")
                val containsInteger = text.`is contain integer`(55)
                return@`text should be` startsWith && containsInteger
            }
        }
    }

    @Test
    fun `test should check initial occupied seats value`() {
        val message = "did you really count an occupied seats?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `for occupied seats view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Occupied seats: ")
                val containsInteger = text.`is contain integer`(0)
                return@`text should be` startsWith && containsInteger
            }
        }
    }

    @Test
    fun `test should check occupied seats value`() {
        val message = "did you really count an occupied seats?"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`().`perform click`()
            `for occupied seats view`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Occupied seats: ")
                val containsInteger = text.`is contain integer`(1)
                return@`text should be` startsWith && containsInteger
            }
        }
    }

    private fun MainActivity.`for total income view`() = find<TextView>("cinema_room_total_income")

    private fun MainActivity.`for current income view`() = find<TextView>("cinema_room_current_income")

    private fun MainActivity.`for available seats view`() = find<TextView>("cinema_room_available_seats")

    private fun MainActivity.`for occupied seats view`() = find<TextView>("cinema_room_occupied_seats")

}