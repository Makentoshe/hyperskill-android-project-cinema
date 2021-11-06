package org.hyperskill.cinema

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

abstract class AbstractUnitTest<T : Activity>(private val activityClass: Class<T>) {

    protected lateinit var activityController: ActivityController<T>
        private set

    protected lateinit var activity: T

    @Before
    fun beforeAbstract() {
        activityController = Robolectric.buildActivity(activityClass)
    }

    protected fun Context.identifier(id: String, `package`: String = packageName) : Int {
        return resources.getIdentifier(id, "id", `package`)
    }

    protected fun identifier(id: String) : Int {
        return activity.resources.getIdentifier(id, "id", activity.packageName)
    }

    protected fun <T: View> View.find(id: String): T = findViewById(context.identifier(id))

    protected fun <T: View> MainActivity.find(id: String) : T = findViewById(identifier(id))

    protected fun <T: View> AlertDialog.find(id: String, `package`: String = context.packageName) : T {
        return findViewById(context.identifier(id, `package`))
    }

    protected fun <T: View> find(id: Int) : T = activity.findViewById(id)

    protected fun <T: View> find(id: String) : T = activity.findViewById(identifier(id))
}