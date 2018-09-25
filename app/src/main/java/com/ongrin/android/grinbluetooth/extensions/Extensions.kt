package com.ongrin.android.grinbluetooth.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("'Created on' MMM dd YYYY 'at' HH:mm:s", Locale.getDefault())
    return format.format(this)
}