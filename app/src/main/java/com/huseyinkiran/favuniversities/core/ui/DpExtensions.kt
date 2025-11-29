package com.huseyinkiran.favuniversities.core.ui

import android.content.Context

fun Int.dpToPx(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()