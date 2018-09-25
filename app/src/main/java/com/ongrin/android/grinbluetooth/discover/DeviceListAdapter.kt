package com.ongrin.android.grinbluetooth.discover

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.ongrin.android.grinbluetooth.R
import com.ongrin.android.grinbluetooth.databinding.RecyclerViewDeviceListItemBinding
import com.ongrin.android.grinbluetooth.extensions.toSimpleString
import com.ongrin.presentation.common.model.DeviceModelView
import java.util.*

class DeviceListAdapter constructor(private val listener: ClickListener<DeviceModelView>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val deviceList: ArrayList<DeviceModelView> = ArrayList()

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
        deviceViewHolder.mBinding.btnSave.setImageResource(R.drawable.ic_add)

        if (device.id != null) {
            deviceViewHolder.mBinding.btnSave.isEnabled = true
            deviceViewHolder.mBinding.btnSave.setImageResource(R.drawable.ic_check)
        }

        deviceViewHolder.mBinding.btnSave.setOnClickListener { button ->
            val imageButton = button as ImageButton
            imageButton.setImageResource(R.drawable.ic_add_pressed)
            listener.onDeviceAdd(button, position, device)
        }
    }

    override fun getItemCount(): Int = deviceList.size

    fun add(item: DeviceModelView) {
        if (deviceList.none { it.address == item.address }) {
            deviceList.add(item)
            notifyItemInserted(deviceList.size - 1)
        }
    }

    fun updateDeviceStatus(device: DeviceModelView) {
        val index = deviceList.indexOfFirst {
            it.address == device.address
        }
        deviceList[index] = device
        notifyItemChanged(index)
    }

    fun clear() {
        deviceList.clear()
        notifyDataSetChanged()
    }

    private class DeviceListItemViewHolder internal constructor(
            var mBinding: RecyclerViewDeviceListItemBinding
    ) : RecyclerView.ViewHolder(mBinding.root)

    interface ClickListener<T> {
        fun onDeviceSelected(view: View, position: Int, model: T)
        fun onDeviceAdd(view: View, position: Int, model: T)
    }
}