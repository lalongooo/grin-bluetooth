package com.ongrin.presentation.browse

import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.BasePresenter
import com.ongrin.presentation.BaseView

interface DeviceListContract {

    interface View : BaseView<Presenter> {
        fun showDeviceList(devices: List<Device>)
        fun showError()
    }

    interface Presenter : BasePresenter {
        fun getDeviceList()
    }
}