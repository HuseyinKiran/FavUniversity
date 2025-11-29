package com.huseyinkiran.favuniversities.core.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.huseyinkiran.favuniversities.R

fun Fragment.openAddressLocation(address: String, view: View) {
    val encoded = Uri.encode(address)
    val geoUri = "geo:0,0?q=$encoded".toUri()
    val intent = Intent(Intent.ACTION_VIEW, geoUri)

    val pm = requireContext().packageManager

    if (intent.resolveActivity(pm) != null) {
        startActivity(intent)
    } else {
        val webUri = "https://www.google.com/maps/search/?api=1&query=$encoded".toUri()
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)

        if (webIntent.resolveActivity(pm) != null) {
            startActivity(webIntent)
        } else {
            Snackbar.make(
                view,
                getString(R.string.error_no_map_or_browser),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}