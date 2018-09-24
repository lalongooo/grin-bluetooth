package com.ongrin.data.device.source

import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.data.device.repository.DeviceCache
import com.ongrin.data.device.repository.DeviceDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DeviceCacheDataStore @Inject constructor(private val deviceCache: DeviceCache) : DeviceDataStore {
    override fun addDevice(device: DeviceEntity): Single<DeviceEntity> {
        return deviceCache.saveDevice(device)
    }

    override fun getDevices(): Single<List<DeviceEntity>> {
        return deviceCache.getDevices()
    }

    override fun clearDevices(): Completable {
        return deviceCache.removeDevices()
    }

}