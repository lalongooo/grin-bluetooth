package com.ongrin.domain.device.repository

import com.ongrin.domain.device.model.Device
import io.reactivex.Single

interface DeviceRepository {
    fun addDevice(device: Device): Single<Device>

    fun getDevices(): Single<List<Device>>
}