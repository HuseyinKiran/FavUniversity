package com.huseyinkiran.favuniversities.utils


class PermissionRepository {

    private var permissionDeniedCount = 0

    fun increaseDeniedCount() {
        permissionDeniedCount++
    }

    fun shouldRedirectToSettings() : Boolean {
        return permissionDeniedCount >= 2
    }

}