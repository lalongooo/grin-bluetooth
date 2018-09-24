package com.ongrin.presentation.discover.mapper

import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.BaseMapper
import com.ongrin.presentation.discover.model.DeviceModelView
import javax.inject.Inject

class DeviceMapper @Inject constructor() : BaseMapper<DeviceModelView, Device> {
    override fun mapFromView(type: DeviceModelView): Device {
        return Device(
                type.id,
                type.name!!,
                type.address!!,
                type.signalStrength!!,
                type.creationDate!!
        )
    }

    override fun mapToView(type: Device): DeviceModelView {
        return DeviceModelView(
                type.id,
                type.name,
                type.address,
                type.signalStrength,
                type.creationDate
        )
    }
}