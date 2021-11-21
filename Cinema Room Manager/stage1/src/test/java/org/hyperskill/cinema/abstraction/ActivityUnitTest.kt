package org.hyperskill.cinema.abstraction

import android.app.Activity
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

/** Class that creates activity controller */
abstract class ActivityUnitTest<T: Activity>(private val activityClass: Class<T>) {

    protected lateinit var activityController: ActivityController<T>
        private set

    @Before
    fun beforeAbstract() {
        activityController = Robolectric.buildActivity(activityClass)
    }
}