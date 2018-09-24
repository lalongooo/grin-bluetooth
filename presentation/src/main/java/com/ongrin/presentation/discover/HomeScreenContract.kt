package com.ongrin.presentation.discover

import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.BasePresenter
import com.ongrin.presentation.BaseView

interface HomeScreenContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun addDevice(device: Device)
    }
}