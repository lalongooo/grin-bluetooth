package com.ongrin.presentation.discover

import com.ongrin.domain.device.model.Device
import com.ongrin.domain.interactor.SingleUseCase
import com.ongrin.presentation.discover.mapper.DeviceMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class HomeScreenPresenter @Inject constructor(
        val homeScreenView: HomeScreenContract.View,
        val addDeviceUseCase: SingleUseCase<Device, Device>,
        val mapper: DeviceMapper) : HomeScreenContract.Presenter {
    override fun start() {
    }

    override fun stop() {
        addDeviceUseCase.dispose()
    }

    override fun addDevice(device: Device) {
        addDeviceUseCase.execute(object : DisposableSingleObserver<Device>() {
            override fun onSuccess(t: Device) {
                homeScreenView.onDeviceAdded(t)
            }

            override fun onError(e: Throwable) {
                println("GrinBT: Error, device NOT added")
            }

        }, device)
    }
}