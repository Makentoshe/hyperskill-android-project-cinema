package org.hyperskill.cinema.abstraction

import android.app.AlertDialog
import android.content.Context
import android.view.View
import org.hyperskill.cinema.MainActivity

interface ViewUnitTest {

    fun Context.identifier(id: String, `package`: String = packageName): Int {
        return resources.getIdentifier(id, "id", `package`)
    }

    fun <T : View> View.find(id: String): T = findViewById(context.identifier(id))

    fun <T : View> MainActivity.find(id: String): T = findViewById(identifier(id))

    fun <T : View> AlertDialog.find(id: String, `package`: String = context.packageName): T {
        return findViewById(context.identifier(id, `package`))
    }
}