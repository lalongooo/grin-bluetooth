package com.ongrin.android.grinbluetooth.browse

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ongrin.android.grinbluetooth.R
import com.ongrin.android.grinbluetooth.databinding.RecyclerViewDeviceListItemBinding
import com.ongrin.android.grinbluetooth.extensions.toSimpleString
import com.ongrin.presentation.common.model.DeviceModelView
import java.util.*
import kotlin.Comparator

class DeviceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        deviceViewHolder.mBinding.btnSave.visibility = View.GONE
    }

    override fun getItemCount(): Int = deviceList.size

    fun add(item: DeviceModelView) {
        if (deviceList.none { it.address == item.address }) {
            deviceList.add(item)
            notifyItemInserted(deviceList.size - 1)
        }
    }

    fun sortByName(ascending: Boolean) {
        deviceList.sortWith(
                Comparator { o1, o2 ->
                    if (ascending) {
                        o1.name!!.compareTo(o2.name!!, true)
                    } else {
                        o2.name!!.compareTo(o1.name!!, true)
                    }
                }
        )
        notifyDataSetChanged()
    }

    fun sortByDate(ascending: Boolean) {
        deviceList.sortWith(
                Comparator { o1, o2 ->
                    if (ascending) {
                        o2.name!!.compareTo(o1.name!!)
                    } else {
                        o1.name!!.compareTo(o2.name!!)
                    }
                }
        )
        notifyDataSetChanged()
    }

    fun clear() {
        deviceList.clear()
        notifyDataSetChanged()
    }

    private class DeviceListItemViewHolder internal constructor(
            var mBinding: RecyclerViewDeviceListItemBinding
    ) : RecyclerView.ViewHolder(mBinding.root)
}