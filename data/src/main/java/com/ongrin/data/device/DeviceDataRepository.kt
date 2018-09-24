package com.ongrin.data.device

import com.ongrin.data.device.mapper.DeviceMapper
import com.ongrin.data.device.source.DeviceDataStoreFactory
import com.ongrin.domain.device.model.Device
import com.ongrin.domain.device.repository.DeviceRepository
import io.reactivex.Single
import javax.inject.Inject

class DeviceDataRepository @Inject constructor(private val factory: DeviceDataStoreFactory,
                                               private val mapper: DeviceMapper) : DeviceRepository {
    override fun addDevice(device: Device): Single<Device> {
        return factory.retrieveDataStore()
                .addDevice(mapper.mapToEntity(device))
                .map {
                    mapper.mapFromEntity(it)
                }
    }

    override fun getDevices(): Single<List<Device>> {
        return factory.retrieveDataStore()
                .getDevices()
                .map { list ->
                    list.map { device -> mapper.mapFromEntity(device) }
                }
    }
}