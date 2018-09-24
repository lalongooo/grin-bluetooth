package com.ongrin.data.device.repository

import com.ongrin.data.device.model.DeviceEntity
import io.reactivex.Completable
import io.reactivex.Single

interface DeviceCache {

    fun saveDevice(device: DeviceEntity): Single<DeviceEntity>

    fun getDevices(): Single<List<DeviceEntity>>

    fun removeDevices(): Completable

}