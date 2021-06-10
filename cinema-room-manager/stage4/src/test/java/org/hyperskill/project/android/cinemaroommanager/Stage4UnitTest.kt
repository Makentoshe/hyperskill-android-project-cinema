package org.hyperskill.project.android.cinemaroommanager

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowAlertDialog

// Version 1.0
@RunWith(RobolectricTestRunner::class)
class Stage4UnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckDialogTitle() {
        val message = "make sure you pass a row number and a place number properly"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        gridLayout.getChildAt(0 * gridLayout.columnCount + 5).performClick()

        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        val titleId: Int = dialog.context.resources.getIdentifier("alertTitle", "id", "android")
        val titleView = dialog.findViewById<TextView>(titleId)

        assertEquals(message, "Buy a ticket 1 row 6 place", titleView.text)
    }

    @Test
    fun testShouldCheckDialogMessage() {
        val message = "make sure you pass a ticket price properly"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        gridLayout.getChildAt(0 * gridLayout.columnCount + 5).performClick()

        val dialog = ShadowAlertDialog.getLatestAlertDialog()
        val messageId: Int = dialog.context.resources.getIdentifier("message", "id", "android")
        val messageView = dialog.findViewById<TextView>(messageId)

        assertEquals(message, "Your ticket price is 24.11\$", messageView.text)
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

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        val gridLayoutChild = gridLayout.getChildAt(0 * gridLayout.columnCount + 5)
        gridLayoutChild.performClick()

        val dialog1 = ShadowAlertDialog.getLatestAlertDialog()
        val positiveButton: Button = dialog1.getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.performClick()

        val indicator = gridLayoutChild.findViewById<CardView>(R.id.cinema_room_place_indicator)
        assertEquals(message1, indicator.cardBackgroundColor.defaultColor, Color.DKGRAY)

        gridLayoutChild.performClick()
        val dialog2 = ShadowAlertDialog.getLatestAlertDialog()
        assertEquals(message2, dialog1, dialog2)
    }

    @Test
    fun testShouldCheckDialogNegativeButton() {
        val message1 = "make sure you do nothing, if purchase was canceled"
        val message2 = "alert dialog should be displayed again if the previous purchase was canceled"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        val gridLayoutChild = gridLayout.getChildAt(0 * gridLayout.columnCount + 5)
        gridLayoutChild.performClick()

        val dialog1 = ShadowAlertDialog.getLatestAlertDialog()
        val negativeButton: Button = dialog1.getButton(Dialog.BUTTON_NEGATIVE)
        negativeButton.performClick()

        val indicator = gridLayoutChild.findViewById<CardView>(R.id.cinema_room_place_indicator)
        assertNotEquals(message1, indicator.cardBackgroundColor.defaultColor, Color.DKGRAY)

        gridLayoutChild.performClick()
        val dialog2 = ShadowAlertDialog.getLatestAlertDialog()
        assertNotEquals(message2, dialog1, dialog2)
    }
}