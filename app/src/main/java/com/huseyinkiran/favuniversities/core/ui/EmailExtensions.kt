package com.huseyinkiran.favuniversities.core.ui

import android.content.Intent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

fun Fragment.openEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:$email".toUri()
        putExtra(Intent.EXTRA_SUBJECT, "")
    }
    if (intent.resolveActivity(requireContext().packageManager) != null) {
        startActivity(intent)
    }
}