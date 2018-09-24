package com.ongrin.remote.device.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class DeviceModel(
        @SerializedName("_id") var id: String,
        @SerializedName("name") var name: String,
        @SerializedName("address") var address: String,
        @SerializedName("strength") var signalStrength: String,
        @SerializedName("created_at") var creationDate: Date
)