package com.ongrin.android.grinbluetooth.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

import javax.inject.Inject

class PermissionsManager @Inject constructor(private val mContext: Context) {

    /**
     * Method to check if the app has permission to access location
     * @return true if the location permission has been granted, false otherwise.
     */
    fun isLocationPermissionGranted(): Boolean {
        return isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    /**
     * Checks if a particular permission has been granted
     * @param permission A string key identifying the android permission.
     * @return True if the permission has been granted, false otherwise.
     */
    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Validate if we should show a rationale dialog for the location permission
     * @param activity The host activity
     * @return True if the permission has been granted, false otherwise.
     */
    fun shouldExplainLocationPermission(activity: Activity): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    /**
     * Show the system dialog to ask the user to grant access to the location
     * @param activity The host activity
     * @param requestCode The location permission request code
     */
    fun showLocationPermissionsDialog(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    requestCode)
        }
    }

    companion object {
        val LOCATION_PERMISSION_REQUEST_CODE = 154
    }
}
