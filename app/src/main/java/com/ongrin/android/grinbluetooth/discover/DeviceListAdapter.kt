package com.ongrin.android.grinbluetooth.discover

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ongrin.android.grinbluetooth.R
import com.ongrin.android.grinbluetooth.common.AdapterClickListener
import com.ongrin.android.grinbluetooth.databinding.RecyclerViewDeviceListItemBinding
import com.ongrin.android.grinbluetooth.extensions.toSimpleString
import java.util.*

data class Device(
        var name: String?,
        var address: String?,
        var signalStrength: String?,
        var creationDate: Date? = null) {
    init {
        name = name ?: "Unnamed device"
        address = address ?: "Unknown address"
        signalStrength = signalStrength ?: "No signal strength"
    }
}

class DeviceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val deviceList: ArrayList<Device> = ArrayList()
    private lateinit var listener: AdapterClickListener<Device>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding: RecyclerViewDeviceListItemBinding = DataBindingUtil.inflate(inflater, R.layout.recycler_view_device_list_item, parent, false)
        return DeviceListItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val device = deviceList[position]
        val deviceViewHolder = holder as DeviceListItemViewHolder
        deviceViewHolder.mBinding.deviceName.text = device.name
        deviceViewHolder.mBinding.deviceAddress.text = device.address
        deviceViewHolder.mBinding.deviceSignalStrength.text = device.signalStrength
        deviceViewHolder.mBinding.deviceCreationDate.text = if (device.creationDate == null) "" else device.creationDate?.toSimpleString()
    }

    override fun getItemCount(): Int = deviceList.size

    fun add(item: Device) {
        deviceList.add(item)
        notifyItemInserted(deviceList.size - 1)
    }

    fun clear() {
        deviceList.clear()
        notifyDataSetChanged()
    }

    fun setAdapterClickListener(adapterClickListener: AdapterClickListener<Device>) {
        listener = adapterClickListener
    }

    private class DeviceListItemViewHolder internal constructor(
            var mBinding: RecyclerViewDeviceListItemBinding) : RecyclerView.ViewHolder(mBinding.root)
}