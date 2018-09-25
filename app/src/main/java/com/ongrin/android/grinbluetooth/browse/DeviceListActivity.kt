package com.ongrin.android.grinbluetooth.browse

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ongrin.android.grinbluetooth.R
import com.ongrin.android.grinbluetooth.databinding.LayoutBottomSheetSortOptionsBinding
import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.browse.DeviceListContract
import com.ongrin.presentation.common.mapper.DeviceMapper
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_device_list.*
import javax.inject.Inject

class DeviceListActivity : AppCompatActivity(), DeviceListContract.View {

    @Inject
    lateinit var mPresenter: DeviceListContract.Presenter

    @Inject
    lateinit var deviceMapper: DeviceMapper

    private var deviceListAdapter: DeviceListAdapter = DeviceListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)
        setSupportActionBar(toolbar)
        setUpUi()
        getDeviceList()
    }

    private fun setUpUi() {
        swipeRefreshLayout.setOnRefreshListener { mPresenter.getDeviceList() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = deviceListAdapter
        title = getString(R.string.device_list)

        var sortByNameAscending = true
        var sortByDateAscending = true

        fab.setOnClickListener {
            val mBottomSheetDialog = BottomSheetDialog(this)
            val sheetView: LayoutBottomSheetSortOptionsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_bottom_sheet_sort_options, null, false)

            sheetView.sortOptionName.setOnClickListener { _ ->
                mBottomSheetDialog.dismiss()
                deviceListAdapter.sortByName(sortByNameAscending)
                sortByNameAscending = !sortByNameAscending
            }

            sheetView.sortOptionCreationDate.setOnClickListener { _ ->
                mBottomSheetDialog.dismiss()
                deviceListAdapter.sortByDate(sortByDateAscending)
                sortByDateAscending = !sortByDateAscending
            }

            mBottomSheetDialog.setContentView(sheetView.root)
            mBottomSheetDialog.show()
        }
    }

    private fun getDeviceList() {
        swipeRefreshLayout.isRefreshing = true
        mPresenter.getDeviceList()
    }

    override fun showDeviceList(devices: List<Device>) {
        swipeRefreshLayout.isRefreshing = false
        deviceListAdapter.clear()
        devices.map { device ->
            deviceListAdapter.add(deviceMapper.mapToView(device))
        }
    }

    override fun showError() {
    }

    override fun setPresenter(presenter: DeviceListContract.Presenter) {
        this.mPresenter = presenter
    }
}