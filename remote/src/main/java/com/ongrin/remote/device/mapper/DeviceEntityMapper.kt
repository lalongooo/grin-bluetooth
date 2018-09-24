package com.ongrin.remote.device.mapper

import com.ongrin.data.device.model.DeviceEntity
import com.ongrin.remote.BaseEntityMapper
import com.ongrin.remote.device.model.DeviceModel

class DeviceEntityMapper : BaseEntityMapper<DeviceModel, DeviceEntity> {
    override fun mapToRemote(type: DeviceEntity): DeviceModel {
        return DeviceModel(
                type.id,
                type.name,
                type.address,
                type.signalStrength,
                type.creationDate
        )
    }

    override fun mapFromRemote(type: DeviceModel): DeviceEntity {
        return DeviceEntity(
                type.id,
                type.name,
                type.address,
                type.signalStrength,
                type.creationDate
        )
    }
}