package org.hyperskill.cinema

import android.app.Dialog
import android.content.Intent
import android.os.Looper
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog

// Version 1.1
@RunWith(RobolectricTestRunner::class)
class Stage5UnitTest {

    private val activityController = Robolectric.buildActivity(MainActivity::class.java)

    @Test
    fun testShouldCheckCinemaInfo1() {
        val message1 = "does you calculate total income properly?"
        val message2 = "does you calculate current income properly?"

        val activity = activityController.apply {
            get().intent = Intent().apply {
                putExtra("DURATION", 90)
                putExtra("RATING", 5.0f)
            }
        }.setup().get()

        val gridLayout = activity.findViewById<GridLayout>(R.id.cinema_room_places)
        val gridLayoutChild = gridLayout.getChildAt(0 * gridLayout.columnCount + 1)

        // Click grid element
        gridLayoutChild.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        // Click positive button in dialog
        val dialog1 = ShadowAlertDialog.getLatestAlertDialog()
        val positiveButton: Button = dialog1.getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.performClick()
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val totalIncomeTextView = activity.findViewById<TextView>(R.id.cinema_room_total_income)
        assertEquals(message1, totalIncomeTextView.text, "Total cinema income: 964.29$")

        val currentIncomeTextView = activity.findViewById<TextView>(R.id.cinema_room_current_income)
        assertEquals(message2, currentIncomeTextView.text, "Current cinema income: 24.11$")

        val availableSeatsTextView = activity.findViewById<TextView>(R.id.cinema_room_available_seats)
        assertEquals(availableSeatsTextView.text, "Available seats: 55")

        val occupiedSeatsTextView = activity.findViewById<TextView>(R.id.cinema_room_occupied_seats)
        assertEquals(occupiedSeatsTextView.text, "Occupied seats: 1")
    }
}