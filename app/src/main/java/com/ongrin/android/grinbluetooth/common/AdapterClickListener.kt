package com.ongrin.android.grinbluetooth.common

import android.view.View

interface AdapterClickListener<T> {
    fun onItemClick(view: View, position: Int, model: T)
}