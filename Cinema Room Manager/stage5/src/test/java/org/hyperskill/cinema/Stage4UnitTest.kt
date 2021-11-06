package org.hyperskill.cinema

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.shadows.ShadowAlertDialog
import java.util.*
import kotlin.math.abs

// Version 6.11.2021
@RunWith(RobolectricTestRunner::class)
class Stage4UnitTest : AbstractUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun testShouldCheckDialogTitle() {
        val message = "make sure you pass a row number and a place number properly"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for dialog title`().`text should be`(message, "Buy a ticket 1 row 6 place")
        }
    }

    @Test
    fun testShouldCheckDialogMessage() {
        val message = "make sure you pass a ticket price properly"

        activityController.`launch this activity and execute`(arguments = `most profitable movie`()) {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for dialog message`().`text should be`(message) { text ->
                val startsWith = text.startsWith("Your ticket price is ")
                val endsWith = text.endsWith("$")
                val double = text.replace("$", "").`is contain double`(expected = 24.11, `with delta` = 0.1)
                return@`text should be` startsWith and endsWith and double
            }
        }
    }

    @Test
    fun testShouldCheckDialogPositiveButton() {
        val message1 = "make sure you change a cinema place availability properly"
        val message2 = "purchased place shouldn't show an alert dialog"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.find<GridLayout>("cinema_room_places")
        val gridLayoutChild = gridLayout.getChildAt(0 * gridLayout.columnCount + 5)

        // Click to grid element to invoke dialog
        gridLayoutChild.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // Click a positive button
        val dialog1 = ShadowAlertDialog.getLatestAlertDialog()
        val positiveButton: Button = dialog1.getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // Check the grid element indicator
        val indicator = gridLayoutChild.find<CardView>("cinema_room_place_indicator")
        assertEquals(message1, indicator.cardBackgroundColor.defaultColor, Color.DKGRAY)

        // Click to grid element to try to invoke dialog again
        gridLayoutChild.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // Check dialog does not appears
        val dialog2 = ShadowAlertDialog.getLatestAlertDialog()
        assertEquals(message2, dialog1, dialog2)
    }

    @Test
    fun testShouldCheckDialogNegativeButtonText() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for negative button`() `text should be` "Cancel purchase"
        }
    }

    @Test
    fun testShouldCheckDialogNegativeButtonVisibility() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for negative button`() `visibility should be` View.VISIBLE
        }
    }

    @Test
    fun testShouldCheckDialogPositiveButtonText() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`() `text should be` "Buy a ticket"
        }
    }

    @Test
    fun testShouldCheckDialogPositiveButtonVisibility() {
        activityController.`launch this activity and execute` {
            `grid layout child`(index = 5).`perform click`()
            `in alert dialog`().`for positive button`() `visibility should be` View.VISIBLE
        }
    }

    @Test
    fun testShouldCheckDialogNegativeButton() {
        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        val gridLayoutChild = gridLayout.getChildAt(0 * gridLayout.columnCount + 5)

        // Click to grid element to invoke dialog
        gridLayoutChild.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        // Click a negative button
        val dialog1 = ShadowAlertDialog.getLatestAlertDialog()
        val negativeButton: Button = dialog1.getButton(Dialog.BUTTON_NEGATIVE)
        negativeButton.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        // Check the grid element indicator
        val indicator = gridLayoutChild.find<CardView>("cinema_room_place_indicator")
        val message1 = """
            Make sure you do nothing, if purchase was canceled (Indicator color and Indication color should not being equal)
            Indicator color: ${indicator.cardBackgroundColor.defaultColor}
            Indication color: ${Color.DKGRAY}
            """.trimIndent()
        assertNotEquals(message1, indicator.cardBackgroundColor.defaultColor, Color.DKGRAY)

        // Click to grid element to try to invoke dialog again
        gridLayoutChild.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        // Check dialog appears
        val dialog2 = ShadowAlertDialog.getLatestAlertDialog()
        val message2 = """
            Alert dialog should be displayed again if the previous purchase was canceled (dialogs should be different)
            First dialog: $dialog1
            Second dialog: $dialog2
        """.trimMargin()
        assertNotEquals(message2, dialog1, dialog2)
    }

    private inline fun <reified A : Activity> ActivityController<A>.`launch this activity and execute`(
        arguments: Intent? = Intent(),
        crossinline action: A.() -> Unit
    ): ActivityController<A> {
        get().intent = arguments
        return setup().also { it.get().apply(action) }
    }

    private fun MainActivity.`grid layout child`(index: Int): View {
        return find<GridLayout>("cinema_room_places").getChildAt(index)
    }

    private fun View.`perform click`() {
        performClick()
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()
    }

    private fun `in alert dialog`(): AlertDialog = ShadowAlertDialog.getLatestAlertDialog()

    private fun AlertDialog.`for negative button`(): Button = getButton(Dialog.BUTTON_NEGATIVE)

    private fun AlertDialog.`for dialog title`(): TextView = find("alertTitle", "android")

    private fun AlertDialog.`for dialog message`(): TextView = find("message", "android")

    private fun AlertDialog.`for positive button`(): Button = getButton(Dialog.BUTTON_POSITIVE)

    private infix fun View.`visibility should be`(visibility: Int) {
        assertEquals(visibility, this.visibility)
    }

    private infix fun TextView.`text should be`(string: String) {
        assertEquals("Expected a $string text in $this", string, text)
    }

    private fun TextView.`text should be`(errorMessage: String, expected: String) {
        assertEquals(errorMessage, expected, text)
    }

    private fun TextView.`text should be`(errorMessage: String, action: (String) -> Boolean) {
        assertTrue(errorMessage, action(text.toString()))
    }

    private fun `most profitable movie`() = Intent().apply {
        putExtra("DURATION", 90)
        putExtra("RATING", 5.0f)
    }

    private fun String.`is contain double`(expected: Double, `with delta`: Double): Boolean {
        val scanner = Scanner(this).useLocale(Locale.US)
        while (scanner.hasNext()) {
            if (scanner.hasNextDouble()) {
                val scanned = scanner.nextDouble()
                if (scanned.compareTo(expected) == 0) return true
                return abs(expected - scanned) > `with delta`
            }
            scanner.next()
        }
        return false
    }

}