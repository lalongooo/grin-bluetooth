package com.ongrin.android.grinbluetooth.di.module

import android.app.Application
import com.ongrin.android.grinbluetooth.di.scope.ActivityScope
import com.ongrin.android.grinbluetooth.discover.MainActivity
import com.ongrin.android.grinbluetooth.manager.PermissionsManager
import com.ongrin.domain.device.interactor.AddDevice
import com.ongrin.presentation.common.mapper.DeviceMapper
import com.ongrin.presentation.discover.HomeScreenContract
import com.ongrin.presentation.discover.HomeScreenPresenter
import dagger.Module
import dagger.Provides

@Module
open class MainActivityModule {

    @ActivityScope
    @Provides
    fun provideHomeScreenView(activity: MainActivity): HomeScreenContract.View {
        return activity
    }

    @ActivityScope
    @Provides
    fun provideHomeScreenPresenter(view: HomeScreenContract.View, addDevice: AddDevice, mapper: DeviceMapper): HomeScreenContract.Presenter {
        return HomeScreenPresenter(view, addDevice, mapper)
    }

    @ActivityScope
    @Provides
    internal fun providesPermissionManager(application: Application): PermissionsManager {
        return PermissionsManager(application)
    }
}