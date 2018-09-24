package com.ongrin.domain.device.interactor

import com.ongrin.domain.device.model.Device
import com.ongrin.domain.device.repository.DeviceRepository
import com.ongrin.domain.executor.PostExecutionThread
import com.ongrin.domain.executor.ThreadExecutor
import com.ongrin.domain.interactor.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for getting all devices
 */
open class GetDeviceList @Inject constructor(private val deviceRepository: DeviceRepository,
                                             threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Device>, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Single<List<Device>> {
        return deviceRepository.getDevices()
    }
}