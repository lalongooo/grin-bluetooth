package com.ongrin.presentation.browse

import com.ongrin.domain.device.model.Device
import com.ongrin.domain.interactor.SingleUseCase
import com.ongrin.presentation.common.mapper.DeviceMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class DeviceListPresenter @Inject constructor(
        val deviceListView: DeviceListContract.View,
        val getDeviceList: SingleUseCase<List<Device>, Void>,
        val mapper: DeviceMapper) : DeviceListContract.Presenter {
    override fun getDeviceList() {
        getDeviceList.execute(object : DisposableSingleObserver<List<Device>>() {
            override fun onSuccess(deviceList: List<Device>) {
                deviceListView.showDeviceList(deviceList)
            }

            override fun onError(e: Throwable) {
                deviceListView.showError()
            }

        }, null)
    }

    override fun start() {
    }

    override fun stop() {
        getDeviceList.dispose()
    }
}