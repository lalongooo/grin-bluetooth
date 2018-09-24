package com.ongrin.data.device.source

import com.ongrin.data.device.repository.DeviceCache
import com.ongrin.data.device.repository.DeviceDataStore
import javax.inject.Inject

class DeviceDataStoreFactory @Inject constructor(
        private val deviceCache: DeviceCache,
        private val deviceCacheDataStore: DeviceCacheDataStore,
        private val deviceRemoteDataStore: DeviceRemoteDataStore) {

    fun retrieveDataStore(): DeviceDataStore {
        // TODO: Implement logic to retrieve data from the cache (local db)
        return deviceRemoteDataStore
    }

}