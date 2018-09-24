package com.ongrin.data.device.repository

import com.ongrin.data.device.model.DeviceEntity
import io.reactivex.Single

interface DeviceRemote {

    fun addDevice(device: DeviceEntity): Single<DeviceEntity>

    fun getDevices(): Single<List<DeviceEntity>>
}