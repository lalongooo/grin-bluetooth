package com.ongrin.data.device.repository

import com.ongrin.data.device.model.DeviceEntity
import io.reactivex.Completable
import io.reactivex.Single

interface DeviceDataStore {
    fun addDevice(device: DeviceEntity): Single<DeviceEntity>

    fun getDevices(): Single<List<DeviceEntity>>

    fun clearDevices(): Completable
}