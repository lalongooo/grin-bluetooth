package com.ongrin.data.device.model

import java.util.Date

data class DeviceEntity(
        var id: String,
        var name: String,
        var address: String,
        var signalStrength: String,
        var creationDate: Date
)