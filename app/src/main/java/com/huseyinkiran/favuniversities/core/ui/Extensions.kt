package com.huseyinkiran.favuniversities.core.ui

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

fun Fragment.callPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phoneNumber".toUri()
    }
    startActivity(intent)
}

fun Int.dpToPx(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()