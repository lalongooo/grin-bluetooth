package com.ongrin.android.grinbluetooth.discover

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.ongrin.android.grinbluetooth.R
import com.ongrin.android.grinbluetooth.browse.DeviceListActivity
import com.ongrin.android.grinbluetooth.manager.GrinPreferenceManager
import com.ongrin.android.grinbluetooth.manager.PermissionsManager
import com.ongrin.domain.device.model.Device
import com.ongrin.presentation.common.mapper.DeviceMapper
import com.ongrin.presentation.common.model.DeviceModelView
import com.ongrin.presentation.discover.HomeScreenContract
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), DeviceListAdapter.ClickListener<DeviceModelView>, HomeScreenContract.View {

    @Inject
    lateinit var mPresenter: HomeScreenContract.Presenter

    @Inject
    lateinit var mPermissionsManager: PermissionsManager

    @Inject
    lateinit var grinPreferenceManager: GrinPreferenceManager

    @Inject
    lateinit var deviceMapper: DeviceMapper

    private var devicesFound = false
    private var deviceListAdapter: DeviceListAdapter = DeviceListAdapter(this)

    private val REQUEST_CODE_ENABLE_BT = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
        mPresenter.stop()
    }

    override fun onStart() {
        super.onStart()
        startBluetooth()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                startBluetoothDiscovery()
            } else {
                disableBluetoothFeature()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val COARSE_LOCATION_PERMISSION_ARRAY_INDEX = 0
        if (grantResults.isNotEmpty()
                && grantResults[COARSE_LOCATION_PERMISSION_ARRAY_INDEX] == PackageManager.PERMISSION_GRANTED) {
            startBluetooth()
        } else {
            showPermissionSnackBar()
        }
    }

    override fun setPresenter(presenter: HomeScreenContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun onDeviceAdded(device: Device) {
        deviceListAdapter.updateDeviceStatus(deviceMapper.mapToView(device))
    }

    override fun onDeviceSelected(view: View, position: Int, model: DeviceModelView) {
        Log.d("GrinBT", "Selected ${model.name} with address: ${model.address}...")
    }

    override fun onDeviceAdd(view: View, position: Int, model: DeviceModelView) {
        mPresenter.addDevice(deviceMapper.mapFromView(model))
    }

    private fun setupUi() {
        // Initialize swipeRefreshLayout state
        swipeRefreshLayout.setOnRefreshListener { startBluetooth() }
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }

        // Initialize recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = deviceListAdapter

        fab.setOnClickListener {
            val intent = Intent(this, DeviceListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startBluetooth() {
        if (mPermissionsManager.isLocationPermissionGranted()) {
            // Check for bluetooth availability on the device
            val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                disableBluetoothFeature()
                return
            }

            // Bluetooth is present, enable it then discover, if it's enabled, start discover
            if (bluetoothAdapter.isEnabled) {
                startBluetoothDiscovery()
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_CODE_ENABLE_BT)
            }
        } else {
            val permissionsDenied = (!(mPermissionsManager.shouldExplainLocationPermission(this)
                    && grinPreferenceManager.hasLocationPermissionBeenRequested())
                    && !mPermissionsManager.shouldExplainLocationPermission(this)
                    && grinPreferenceManager.hasLocationPermissionBeenRequested())
            if (permissionsDenied) {
                showPermissionSnackBar()
            } else {
                grinPreferenceManager.setLocationPermissionHasBeenRequested()
                mPermissionsManager.showLocationPermissionsDialog(this, PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun startBluetoothDiscovery() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, intentFilter)

        val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // If we're already discovering, stop it
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        val discoveryStarted = bluetoothAdapter.startDiscovery()
        if (discoveryStarted) {
            Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.searching_devices, Snackbar.LENGTH_SHORT)
                    .show()
            deviceListAdapter.clear()
            swipeRefreshLayout.isRefreshing = true
            layoutNoDevices.visibility = View.GONE
        }
    }

    private fun disableBluetoothFeature() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.no_bluetooth, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snack_bar_action_enable_bt) { startBluetooth() }
                .show()
    }

    private fun showPermissionSnackBar() {
        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.require_location_permission, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snack_bar_action_ask_permission) {

                    val permissionsDenied = (
                            !(mPermissionsManager.shouldExplainLocationPermission(this)
                                    && grinPreferenceManager.hasLocationPermissionBeenRequested()
                                    )
                                    && !mPermissionsManager.shouldExplainLocationPermission(this)
                                    && grinPreferenceManager.hasLocationPermissionBeenRequested())
                    if (permissionsDenied) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        mPermissionsManager.showLocationPermissionsDialog(this, PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE)
                    }
                }
                .show()
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice object and its info from the Intent.
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)
                    val deviceAddress = device.address // MAC address
                    val deviceSignalStrength = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                    devicesFound = true
                    deviceListAdapter.add(
                            DeviceModelView(
                                    name = deviceName,
                                    address = deviceAddress,
                                    signalStrength = deviceSignalStrength.toString(),
                                    creationDate = Date()
                            )
                    )
                    Log.d("GrinBT", "Bluetooth device found. MacAddress: $deviceAddress, Name: $deviceName, RSSI: $deviceSignalStrength")
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d("GrinBT", "Bluetooth discovery started")
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("GrinBT", "Bluetooth discovery finished")
                    if (swipeRefreshLayout.isRefreshing) {
                        swipeRefreshLayout.isRefreshing = false
                    }
                    if (!devicesFound) {
                        layoutNoDevices.visibility = View.VISIBLE
                    }
                    devicesFound = false
                }
            }
        }
    }
}
