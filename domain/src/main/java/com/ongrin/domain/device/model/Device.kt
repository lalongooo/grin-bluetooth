package com.ongrin.domain.device.model

import java.util.*

data class Device(
        var id: String,
        var name: String,
        var address: String,
        var signalStrength: String,
        var creationDate: Date
)