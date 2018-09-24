package com.ongrin.data.device.source

import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.data.device.repository.DeviceDataStore
import com.ongrin.data.device.repository.DeviceRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DeviceRemoteDataStore @Inject constructor(private val deviceRemote: DeviceRemote) : DeviceDataStore {
    override fun addDevice(device: DeviceEntity): Single<DeviceEntity> {
        return deviceRemote.addDevice(device)
    }

    override fun getDevices(): Single<List<DeviceEntity>> {
        return deviceRemote.getDevices()
    }

    override fun clearDevices(): Completable {
        throw UnsupportedOperationException()
    }
}