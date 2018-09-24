package com.ongrin.android.grinbluetooth.di.module

import android.app.Application
import android.content.Context
import com.ongrin.android.grinbluetooth.BuildConfig
import com.ongrin.android.grinbluetooth.UiThread
import com.ongrin.android.grinbluetooth.di.scope.ApplicationScope
import com.ongrin.cache.DeviceCacheImpl
import com.ongrin.data.device.DeviceDataRepository
import com.ongrin.data.device.mapper.DeviceMapper
import com.ongrin.data.device.repository.DeviceCache
import com.ongrin.data.device.repository.DeviceRemote
import com.ongrin.data.device.source.DeviceDataStoreFactory
import com.ongrin.data.executor.JobExecutor
import com.ongrin.domain.device.repository.DeviceRepository
import com.ongrin.domain.executor.PostExecutionThread
import com.ongrin.domain.executor.ThreadExecutor
import com.ongrin.remote.GrinBluetoothDeviceService
import com.ongrin.remote.GrinBluetoothDeviceServiceFactory
import com.ongrin.remote.device.DeviceRemoteImpl
import com.ongrin.remote.device.mapper.DeviceEntityMapper
import dagger.Module
import dagger.Provides

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @ApplicationScope
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @ApplicationScope
    fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @ApplicationScope
    fun provideDeviceRepository(factory: DeviceDataStoreFactory, mapper: DeviceMapper): DeviceRepository {
        return DeviceDataRepository(factory, mapper)
    }

    @Provides
    @ApplicationScope
    fun provideDeviceCache(): DeviceCache {
        return DeviceCacheImpl()
    }

    @Provides
    @ApplicationScope
    fun provideDeviceRemote(service: GrinBluetoothDeviceService, mapper: DeviceEntityMapper): DeviceRemote {
        return DeviceRemoteImpl(service, mapper)
    }

    @Provides
    @ApplicationScope
    fun provideDeviceService(): GrinBluetoothDeviceService {
        return GrinBluetoothDeviceServiceFactory.makeBluetoothApiService(BuildConfig.DEBUG)
    }
}