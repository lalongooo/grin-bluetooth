package com.ongrin.android.grinbluetooth.di.module

import com.ongrin.android.grinbluetooth.browse.DeviceListActivity
import com.ongrin.android.grinbluetooth.discover.MainActivity
import com.ongrin.android.grinbluetooth.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(DeviceListActivityModule::class)])
    abstract fun bindDeviceListActivity(): DeviceListActivity
}