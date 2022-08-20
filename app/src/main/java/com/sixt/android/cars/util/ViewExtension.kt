package com.sixt.android.cars.util

import android.app.Activity
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.sixt.android.cars.R


fun Activity.makeStatusBarTransparent() {
    window.apply {

        //WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightNavigationBars = true
        windowInsetsController.isAppearanceLightStatusBars = true
        statusBarColor = ContextCompat.getColor(this.context, R.color.colorPrimaryDark)
    }
}

fun Activity.showErrorMessageInDialog(
    heading: String? = "Error",
    errorMessage: String
) {
    this.let { callingContext ->

        AlertDialog.Builder(callingContext)
            .setTitle(heading)
            .setMessage(errorMessage)
            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                callingContext.getString(android.R.string.ok), null
            )
            .show()
    }
}

fun Activity.showToast(
    toastMessage: String
) {
    this.let { callingContext ->

        Toast.makeText(callingContext, toastMessage, Toast.LENGTH_LONG).show()
    }
}



