package com.ongrin.presentation.common.model

import java.util.Date

data class DeviceModelView(
        var id: String? = null,
        var name: String?,
        var address: String?,
        var signalStrength: String?,
        var creationDate: Date? = null) {
    init {
        name = name ?: "Unnamed device"
        address = address ?: "Unknown address"
        signalStrength = signalStrength ?: "No signal strength"
    }
}