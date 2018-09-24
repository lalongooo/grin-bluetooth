package com.ongrin.cache

import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.data.device.repository.DeviceCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DeviceCacheImpl @Inject constructor() : DeviceCache {
    override fun saveDevice(device: DeviceEntity): Single<DeviceEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDevices(): Single<List<DeviceEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeDevices(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}