package com.ongrin.android.grinbluetooth.di.module

import com.ongrin.android.grinbluetooth.browse.DeviceListActivity
import com.ongrin.android.grinbluetooth.di.scope.ActivityScope
import com.ongrin.domain.device.interactor.GetDeviceList
import com.ongrin.presentation.browse.DeviceListContract
import com.ongrin.presentation.browse.DeviceListPresenter
import com.ongrin.presentation.common.mapper.DeviceMapper
import dagger.Module
import dagger.Provides

@Module
open class DeviceListActivityModule {

    @ActivityScope
    @Provides
    fun provideDeviceListView(activity: DeviceListActivity): DeviceListContract.View {
        return activity
    }

    @ActivityScope
    @Provides
    fun provideDeviceListPresenter(view: DeviceListContract.View, getDeviceList: GetDeviceList, mapper: DeviceMapper): DeviceListContract.Presenter {
        return DeviceListPresenter(view, getDeviceList, mapper)
    }
}