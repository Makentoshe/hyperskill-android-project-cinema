package org.hyperskill.cinema

import android.app.Activity
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

    protected fun identifier(id: String) : Int {
        return activity.resources.getIdentifier(id, "id", activity.packageName)
    }

    protected fun <T: View> find(id: Int) : T? = activity.findViewById(id)

    protected fun <T: View> Activity.find(id: Int) : T? = findViewById(id)

    protected fun <T: View> find(id: String) : T? = activity.findViewById(identifier(id))

    protected fun <T: View> Activity.find(id: String) : T? = findViewById(identifier(id))
}