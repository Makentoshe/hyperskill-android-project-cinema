package org.hyperskill.cinema

import android.graphics.Color
import android.view.View
import org.hyperskill.cinema.abstraction.AbstractUnitTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

// Version 21.11.2021
@RunWith(RobolectricTestRunner::class)
class Stage4UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun `test should check dialog's title text`() {
        val message = "make sure you pass a row number and a place number properly into dialog"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for dialog title`()
                .`text should be`(message, "Buy a ticket 1 row 6 place")
        }
    }

    @Test
    fun `test should check dialog's message text`() {
        val message = "make sure you pass a ticket price properly into dialog"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for dialog message`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Your ticket price is ")
                val endsWith = text.endsWith("$")
                val double =
                    text.replace("$", "").`is contain double`(expected = 24.11, `with delta` = 0.1)
                return@`text should be` startsWith and endsWith and double
            }
        }
    }

    @Test
    fun `test should check booked place color via dialog`() {
        val message =
            "make sure you change a purchased cinema place indicator via cardBackgroundColor"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`().`perform click`()
            `grid layout child`(index = 5).indicator().`color should be`(message, Color.DKGRAY)
        }
    }

    @Test
    fun `test should check booked place availability via dialog`() {
        val message = "purchased place shouldn't show an alert dialog on click"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            val firstAlertDialog = `in alert dialog`().apply {
                `for positive button`().`perform click`()
            }

            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`should be same as`(message, firstAlertDialog)
        }
    }

    @Test
    fun `test should check dialog's negative button text`() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for negative button`() `text should be` "Cancel purchase"
        }
    }

    @Test
    fun `test should check dialog's negative button visibility`() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for negative button`() `visibility should be` View.VISIBLE
        }
    }

    @Test
    fun `test should check dialog's positive button text`() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`() `text should be` "Buy a ticket"
        }
    }

    @Test
    fun `test should check dialog's positive button visibility`() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`() `visibility should be` View.VISIBLE
        }
    }

    @Test
    fun `test should check canceled booking place color via dialog`() {
        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for negative button`().`perform click`()

            `grid layout child`(index = 5).indicator().`color shouldn't be`(Color.DKGRAY) {
                """
                    Make sure you do nothing, if purchase was canceled (Indicator color and Indication color should not being equal)
                    Indicator color: ${it.cardBackgroundColor.defaultColor}
                    Indication color: ${Color.DKGRAY}
                """.trimIndent()
            }
        }
    }

    @Test
    fun `test should check canceled booking place availability via dialog`() {
        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            val firstAlertDialog = `in alert dialog`().apply {
                `for negative button`().`perform click`()
            }

            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`shouldn't be same as`(firstAlertDialog) { secondAlertDialog ->
                """
                    Alert dialog should be displayed again if the previous purchase was canceled (dialogs should be different)
                    First dialog: $firstAlertDialog
                    Second dialog: $secondAlertDialog
                """.trimMargin()
            }
        }
    }

}