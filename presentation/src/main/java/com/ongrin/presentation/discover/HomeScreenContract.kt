package com.ongrin.presentation.discover

import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.BasePresenter
import com.ongrin.presentation.BaseView

interface HomeScreenContract {
    interface View : BaseView<Presenter> {
        fun onDeviceAdded(device: Device)
    }

    interface Presenter : BasePresenter {
        fun addDevice(device: Device)
    }
}