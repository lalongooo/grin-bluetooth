package com.ongrin.remote.device

import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.data.device.repository.DeviceRemote
import com.ongrin.remote.GrinBluetoothDeviceService
import com.ongrin.remote.device.mapper.DeviceEntityMapper
import io.reactivex.Single
import javax.inject.Inject

class DeviceRemoteImpl @Inject constructor(private val service: GrinBluetoothDeviceService,
                                           private val mapper: DeviceEntityMapper) : DeviceRemote {
    override fun addDevice(device: DeviceEntity): Single<DeviceEntity> {
        return service.addDevice(mapper.mapToRemote(device))
                .map {
                    mapper.mapFromRemote(it)
                }
    }

    override fun getDevices(): Single<List<DeviceEntity>> {
        return service.getDevices()
                .map { list ->
                    list.map { mapper.mapFromRemote(it) }
                }
    }
}