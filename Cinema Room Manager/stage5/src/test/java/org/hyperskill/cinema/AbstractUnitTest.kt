package org.hyperskill.cinema

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import org.junit.Assert
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.shadows.ShadowAlertDialog
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs

abstract class AbstractUnitTest<T : Activity>(private val activityClass: Class<T>) {

    protected lateinit var activityController: ActivityController<T>
        private set

    protected lateinit var activity: T

    @Before
    fun beforeAbstract() {
        activityController = Robolectric.buildActivity(activityClass)
    }

    protected fun Context.identifier(id: String, `package`: String = packageName): Int {
        return resources.getIdentifier(id, "id", `package`)
    }

    protected fun identifier(id: String): Int {
        return activity.resources.getIdentifier(id, "id", activity.packageName)
    }

    protected fun <T : View> View.find(id: String): T = findViewById(context.identifier(id))

    protected fun <T : View> MainActivity.find(id: String): T = findViewById(identifier(id))

    protected fun <T : View> AlertDialog.find(
        id: String,
        `package`: String = context.packageName
    ): T {
        return findViewById(context.identifier(id, `package`))
    }

    protected fun <T : View> find(id: Int): T = activity.findViewById(id)

    protected fun <T : View> find(id: String): T = activity.findViewById(identifier(id))

    protected fun `most profitable movie`() = Intent().apply {
        putExtra("DURATION", 90)
        putExtra("RATING", 5.0f)
    }

    protected fun `default profitable movie`() = Intent().apply {
        putExtra("DURATION", 108)
        putExtra("RATING", 4.5f)
    }

    protected inline fun <reified A : Activity> ActivityController<A>.`launch this activity and execute`(
        arguments: Intent? = Intent(),
        crossinline action: A.() -> Unit
    ): ActivityController<A> {
        get().intent = arguments
        return setup().also { it.get().apply(action) }
    }

    protected fun MainActivity.`grid layout child`(index: Int): View {
        return find<GridLayout>("cinema_room_places").getChildAt(index)
    }

    protected fun View.`perform click`() {
        performClick()
        Shadows.shadowOf(Looper.getMainLooper()).runToEndOfTasks()
    }

    protected fun `in alert dialog`(): AlertDialog = ShadowAlertDialog.getLatestAlertDialog()

    protected fun AlertDialog.`for positive button`(): Button = getButton(Dialog.BUTTON_POSITIVE)

    protected fun AlertDialog.`for negative button`(): Button = getButton(Dialog.BUTTON_NEGATIVE)


    protected fun TextView.`text should be`(error: String, action: (String) -> Boolean) {
        Assert.assertTrue(error, action(text.toString()))
    }

    protected fun String.`is contain double`(expected: Double, `with delta`: Double): Boolean {
        println(this)
        val matcher: Matcher = Pattern.compile("([0-9.]*[0-9]+)").matcher(this)
        while (matcher.find()) {
            val scanned = matcher.group(1)?.toDoubleOrNull() ?: continue
            println(scanned)
            if (abs(expected - scanned) < `with delta`) return true
        }
        return false
    }

    protected fun String.`is contain integer`(expected: Int): Boolean {
        val matcher: Matcher = Pattern.compile("([0-9]+)").matcher(this)
        while (matcher.find()) {
            val scanned = matcher.group(1)?.toIntOrNull() ?: continue
            if (expected == scanned) return true
        }
        return false
    }
}