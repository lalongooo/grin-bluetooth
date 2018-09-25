package com.ongrin.android.grinbluetooth.manager

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class GrinPreferenceManager @Inject constructor(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Preference keys
    private val PREFERENCE_KEY_PERMISSION_LOCATION_REQUESTED = "pref.permission.location.requested"

    fun hasLocationPermissionBeenRequested(): Boolean {
        return sharedPreferences.getBoolean(PREFERENCE_KEY_PERMISSION_LOCATION_REQUESTED, false)
    }

    fun setLocationPermissionHasBeenRequested() {
        sharedPreferences.edit()
                .putBoolean(PREFERENCE_KEY_PERMISSION_LOCATION_REQUESTED, true)
                .apply()
    }

}