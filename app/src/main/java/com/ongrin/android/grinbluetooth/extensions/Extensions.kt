package com.ongrin.android.grinbluetooth.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("MMM dd YYYY 'at' HH:mm", Locale.getDefault())
    return format.format(this)
}