package com.ongrin.remote

import com.ongrin.remote.device.model.DeviceModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GrinBluetoothDeviceService {
    @GET("devices")
    fun getDevices(): Single<List<DeviceModel>>

    @POST("add")
    fun addDevice(@Body device: DeviceModel): Single<DeviceModel>
}