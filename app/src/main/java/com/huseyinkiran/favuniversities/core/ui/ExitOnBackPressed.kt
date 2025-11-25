package com.huseyinkiran.favuniversities.core.ui

import androidx.activity.addCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

fun exitOnBackPressed(requireActivity: FragmentActivity, viewLifecycleOwner: LifecycleOwner) {
    requireActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        requireActivity.finish()
    }
}