package com.huseyinkiran.favuniversities.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.huseyinkiran.favuniversities.R

object CallPermissionDialog {

    fun showPermissionRationaleDialog(context: Context, onPositiveClick: () -> Unit) {
        AlertDialog.Builder(context).setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_positive_text) { _, _ ->
                onPositiveClick()
            }
            .setNegativeButton(R.string.dialog_negative_text, null)
            .show()
    }

    fun showGoToSettingsDialog(context: Context) {
        AlertDialog.Builder(context).setTitle(R.string.settings_dialog_title)
            .setMessage(R.string.settings_dialog_message)
            .setPositiveButton(R.string.settings_dialog_positive_text) { _,_ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
            }
            .setNegativeButton(R.string.dialog_negative_text,null)
            .show()
    }

}