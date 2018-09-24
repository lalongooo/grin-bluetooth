package com.ongrin.data.device.mapper

import com.ongrin.data.BaseMapper
import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.domain.device.model.Device
import javax.inject.Inject

class DeviceMapper @Inject constructor() : BaseMapper<DeviceEntity, Device> {
    override fun mapFromEntity(type: DeviceEntity): Device {
        return Device(
                type.id,
                type.name,
                type.address,
                type.signalStrength,
                type.creationDate
        )
    }

    override fun mapToEntity(type: Device): DeviceEntity {
        return DeviceEntity(
                type.id,
                type.name,
                type.address,
                type.signalStrength,
                type.creationDate
        )
    }
}